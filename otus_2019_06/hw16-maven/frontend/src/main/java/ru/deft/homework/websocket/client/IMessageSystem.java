package ru.deft.homework.websocket.client;

import ru.deft.homework.websocket.domain.Message;

/*
 * Created by sgolitsyn on 12/7/19
 */
public interface IMessageSystem {

    public void sendRequestToMessageSystem(Message message);
}
