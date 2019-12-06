package ru.deft.homework.messagesystem;


import ru.deft.homework.messagesystem.model.Message;

import java.util.Optional;

public interface RequestHandler {
  Optional<Message> handle(Message msg);
}
