package ru.deft.homework.front;


import ru.deft.homework.messagesystem.MsClient;
import ru.deft.homework.messagesystem.model.Message;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FrontendService {
    void getUserData(long userId, Consumer<Message> dataConsumer);

    <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);

    void setMsClient(MsClient msClient);

    void getUserData(String from, String content, Consumer<Message> dataConsumer);
}

