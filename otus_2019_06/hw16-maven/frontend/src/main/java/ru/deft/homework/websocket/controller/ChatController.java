package ru.deft.homework.websocket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ru.deft.homework.websocket.client.IMessageSystem;
import ru.deft.homework.websocket.domain.Message;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private final IMessageSystem messageSystem;

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public void getMessages(Message message) {
        log.debug(message.toString());
        messageSystem.sendRequestToMessageSystem(message);
//        return message;
    }

    @MessageMapping("/responce")
    @SendTo("/chat/messages")
    public Message sendToChat(Message message) {
        log.debug("/chat/messages {}", message);
        return message;
    }
}
