package ru.deft.homework.client;

import ru.deft.homework.messagesystem.messagesystem.model.Message;

/*
 * Created by sgolitsyn on 12/8/19
 */
public interface FrontendServiceResponce {
    void sendResponceToFrontend(Message message);
}
