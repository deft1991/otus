package ru.deft.homework.messagesystem.messagesystem.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.deft.homework.messagesystem.messagesystem.MessageSystem;
import ru.deft.homework.messagesystem.messagesystem.MsClient;
import ru.deft.homework.messagesystem.messagesystem.model.Message;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public final class MessageSystemImpl implements MessageSystem {
    private static final Logger logger = LoggerFactory.getLogger(MessageSystemImpl.class);
    private static final int MESSAGE_QUEUE_SIZE = 1_000;
    private static final int MSG_HANDLER_THREAD_LIMIT = 2;

    private final AtomicBoolean runFlag = new AtomicBoolean(true);

    private final Map<String, MsClient> clientMap = new ConcurrentHashMap<>();
    private final BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(MESSAGE_QUEUE_SIZE);

    private final ExecutorService msgProcessor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("msg-processor-thread");
        return thread;
    });

    private final ExecutorService msgHandler = Executors.newFixedThreadPool(MSG_HANDLER_THREAD_LIMIT, new ThreadFactory() {
        private final AtomicInteger threadNameSeq = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("msg-handler-thread-" + threadNameSeq.incrementAndGet());
            return thread;
        }
    });

    public void start() {
        new Thread(() -> msgProcessor.submit(this::msgProcessor)).start();
    }

    private void msgProcessor() {
        logger.info("msgProcessor started");
        while (runFlag.get()) {
            try {
                if (messageQueue.peek() != null) {
                    Message msg = messageQueue.take();
                    if ("STOP".equals(msg.getMessage())) {
                        logger.info("received the stop message");
                    } else {
                        // find client to
                        MsClient clientTo = clientMap.get(msg.getFrom());
                        if (clientTo == null) {
                            logger.warn("client not found");
                        } else {
                            msgHandler.submit(() -> handleMessage(clientTo, msg));
                        }
                    }
                }
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage(), ex);
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        msgHandler.submit(this::messageHandlerShutdown);
        logger.info("msgProcessor finished");

    }

    private void messageHandlerShutdown() {
        msgHandler.shutdown();
        logger.info("msgHandler has been shut down");
    }


    private void handleMessage(MsClient msClient, Message msg) {
        try {
            System.out.println(msClient.getName());
            msClient.handle(msg);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            logger.error("message:{}", msg);
        }
    }

    private void insertStopMessage() throws InterruptedException {
        boolean result = messageQueue.offer(new Message("STOP"));
        while (!result) {
            Thread.sleep(100);
            result = messageQueue.offer(new Message("STOP"));
        }
    }


    @Override
    public void addClient(MsClient msClient) {
        logger.info("new client:{}", msClient.getName());
        if (clientMap.containsKey(msClient.getName())) {
            throw new IllegalArgumentException("Error. client: " + msClient.getName() + " already exists");
        }
        clientMap.put(msClient.getName(), msClient);
    }

    @Override
    public void removeClient(String clientId) {
        MsClient removedClient = clientMap.remove(clientId);
        if (removedClient == null) {
            logger.warn("client not found: {}", clientId);
        } else {
            logger.info("removed client:{}", removedClient);
        }
    }

    @Override
    public boolean newMessage(Message msg) {
        if (runFlag.get()) {
            return messageQueue.offer(msg);
        } else {
            logger.warn("MS is being shutting down... rejected:{}", msg);
            return false;
        }
    }

    @Override
    public void dispose() throws InterruptedException {
        runFlag.set(false);
        insertStopMessage();
        msgProcessor.shutdown();
        msgHandler.awaitTermination(60, TimeUnit.SECONDS);
    }


}
