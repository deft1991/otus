package ru.deft.homework.messagesystem.front.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.deft.homework.client.FrontendServiceResponce;
import ru.deft.homework.messagesystem.messagesystem.RequestHandler;
import ru.deft.homework.messagesystem.messagesystem.model.Message;

import java.util.Optional;

public class GetUserDataResponseHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(GetUserDataResponseHandler.class);

    private final FrontendServiceResponce frontendServiceResponce;

    public GetUserDataResponseHandler(FrontendServiceResponce frontendServiceResponce) {
        this.frontendServiceResponce = frontendServiceResponce;
    }

    @Override
    public Optional<Message> handle(Message message) {
        logger.info("new message:{}", message);
        frontendServiceResponce.sendResponceToFrontend(message);
        return Optional.of(new Message("return", message.getMessage() + "--> From GetUserDataResponseHandler"));
    }
}
