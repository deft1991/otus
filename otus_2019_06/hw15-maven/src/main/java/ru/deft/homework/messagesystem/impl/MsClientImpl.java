package ru.deft.homework.messagesystem.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.deft.homework.messagesystem.MessageSystem;
import ru.deft.homework.messagesystem.MsClient;
import ru.deft.homework.messagesystem.RequestHandler;
import ru.deft.homework.messagesystem.model.Message;
import ru.deft.homework.messagesystem.model.MessageType;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MsClientImpl implements MsClient {
    private static final Logger logger = LoggerFactory.getLogger(MsClientImpl.class);

    private final String name;
    private MessageSystem messageSystem;
    private final Map<String, RequestHandler> handlers = new ConcurrentHashMap<>();


    public MsClientImpl(String name) {
        this.name = name;
    }

    public MsClientImpl(String name, MessageSystem messageSystem) {
        this.name = name;
        this.messageSystem = messageSystem;
    }

    @Override
    public void addHandler(MessageType type, RequestHandler requestHandler) {
        this.handlers.put(type.getValue(), requestHandler);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean sendMessage(Message msg) {
        boolean result = messageSystem.newMessage(msg);
        if (!result) {
            logger.error("the last message was rejected: {}", msg);
        }
        return result;
    }

    @Override
    public void handle(Message msg) {
        logger.info("new message:{}", msg);
        try {
            RequestHandler requestHandler = handlers.get(msg.getType());
            if (requestHandler != null) {
                requestHandler.handle(msg).ifPresent(this::sendMessage);
            } else {
                logger.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            logger.error("msg:" + msg, ex);
        }
    }

    @Override
    public <T> Message produceMessage(String to, T data, MessageType msgType) {
        return new Message(name, to, msgType.getValue(), data);
    }

    @Override
    public void setMessageSystem(MessageSystem messageSystem) {
        if (this.messageSystem == null) {
            this.messageSystem = messageSystem;
        }
    }

    @Override
    public Message produceMessage(String to, String from, String content, MessageType userData) {
        return new Message(name, to, userData.getValue(), content);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientImpl msClient = (MsClientImpl) o;
        return Objects.equals(name, msClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
