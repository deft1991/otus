package ru.deft.homework.messagesystem.front.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.deft.homework.messagesystem.front.FrontendService;
import ru.deft.homework.messagesystem.messagesystem.MsClient;
import ru.deft.homework.messagesystem.messagesystem.model.Message;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class FrontendServiceImpl implements FrontendService {
    private static final Logger logger = LoggerFactory.getLogger(FrontendServiceImpl.class);

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private MsClient msClient;


    public FrontendServiceImpl(MsClient msClient) {
        this.msClient = msClient;
    }

    @Override
    public void getUserData(long userId, Consumer<Message> dataConsumer) {

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

    @Override
    public void getUserData(String from, String content, Consumer<Message> dataConsumer) {

    }
}
