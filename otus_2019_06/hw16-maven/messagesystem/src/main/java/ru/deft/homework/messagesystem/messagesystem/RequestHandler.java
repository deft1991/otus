package ru.deft.homework.messagesystem.messagesystem;



import ru.deft.homework.messagesystem.messagesystem.model.Message;

import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
