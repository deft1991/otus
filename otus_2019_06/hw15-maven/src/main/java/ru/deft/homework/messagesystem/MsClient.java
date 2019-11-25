package ru.deft.homework.messagesystem;

import ru.deft.homework.messagesystem.model.Message;
import ru.deft.homework.messagesystem.model.MessageType;

public interface MsClient {

  void addHandler(MessageType type, RequestHandler requestHandler);

  boolean sendMessage(Message msg);

  void handle(Message msg);

  String getName();

  <T> Message produceMessage(String to, T data, MessageType msgType);

  void setMessageSystem(MessageSystem messageSystem);

  Message produceMessage(String databaseServiceClientName, String from, String content, MessageType userData);
}
