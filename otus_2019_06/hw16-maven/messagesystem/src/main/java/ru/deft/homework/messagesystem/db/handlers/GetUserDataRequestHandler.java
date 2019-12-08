package ru.deft.homework.messagesystem.db.handlers;

import lombok.extern.slf4j.Slf4j;
import ru.deft.homework.client.DBServiceClient;
import ru.deft.homework.messagesystem.messagesystem.RequestHandler;
import ru.deft.homework.messagesystem.messagesystem.model.Message;

import java.util.Optional;

@Slf4j
public class GetUserDataRequestHandler implements RequestHandler {
    private final DBServiceClient dbService;

    public GetUserDataRequestHandler(DBServiceClient dbService) {
        this.dbService = dbService;
    }

    @Override
    public Optional<Message> handle(Message message) {
        log.debug("in GetUserDataRequestHandler.handle message --> {}", message);
        return Optional.of(dbService.saveMessageInDbRequest(message));

    }
}
