package ru.deft.homework.messagesystem;

import ru.deft.homework.messagesystem.model.Message;

public interface MessageSystem {

  void addClient(MsClient msClient);

  void removeClient(String clientId);

  boolean newMessage(Message msg);

  void dispose() throws InterruptedException;

  void start();
}

