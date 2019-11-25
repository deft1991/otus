package ru.deft.homework.db.handlers;

import ru.deft.homework.db.DBService;
import ru.deft.homework.ioc.IoC;
import ru.deft.homework.messagesystem.RequestHandler;
import ru.deft.homework.messagesystem.model.Message;
import ru.deft.homework.messagesystem.model.MessageType;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler {
    private final DBService dbService;
    private static IoC ioC = new IoC();

    public GetUserDataRequestHandler(DBService dbService) {
        this.dbService = dbService;
    }

//  @Override
//  public Optional<Message> handle(Message msg) {
//    long id = Serializers.deserialize(msg.getPayload(), Long.class);
//    String data = dbService.getUserData(id);
//    return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), MessageType.USER_DATA.getValue(), Serializers.serialize(data)));
//  }

    @Override
    public Optional<Message> handle(Message msg) {
        ru.deft.homework.api.model.Message message = new ru.deft.homework.api.model.Message(msg.getId(), msg.getContent());
        ioC.getDbMessageService().save(message);
        return Optional.of(new Message(msg.getTo()
                , msg.getFrom()
                , Optional.of(message.getId())
                , MessageType.USER_DATA.getValue()
                , message.getMessageText()));
    }
}
