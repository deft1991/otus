package ru.deft.homework.front.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.deft.homework.front.FrontendService;
import ru.deft.homework.messagesystem.MsClient;
import ru.deft.homework.messagesystem.model.Message;
import ru.deft.homework.messagesystem.model.MessageType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class FrontendServiceImpl implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(String databaseServiceClientName) {
        this.databaseServiceClientName = databaseServiceClientName;
    }

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getUserData(long userId, Consumer<Message> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, userId, MessageType.USER_DATA);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
        if (consumer == null) {
            logger.warn("consumer not found for:{}", sourceMessageId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }

    @Override
    public void setMsClient(MsClient msClient) {
        this.msClient = msClient;
    }
}
