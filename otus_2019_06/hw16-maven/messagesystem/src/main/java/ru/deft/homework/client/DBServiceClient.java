package ru.deft.homework.client;

import ru.deft.homework.messagesystem.messagesystem.model.Message;

/*
 * Created by sgolitsyn on 12/7/19
 */
public interface DBServiceClient {
    Message saveMessageInDbRequest(Message message);
}
