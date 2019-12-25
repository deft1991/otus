package ru.deft.homework.messagesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.deft.homework.client.DBServiceClient;
import ru.deft.homework.client.impl.FrontendServiceResponceImpl;
import ru.deft.homework.messagesystem.db.handlers.GetUserDataRequestHandler;
import ru.deft.homework.messagesystem.front.handlers.GetUserDataResponseHandler;
import ru.deft.homework.messagesystem.messagesystem.MessageSystem;
import ru.deft.homework.messagesystem.messagesystem.MsClient;
import ru.deft.homework.messagesystem.messagesystem.impl.MessageSystemImpl;
import ru.deft.homework.messagesystem.messagesystem.impl.MsClientImpl;
import ru.deft.homework.messagesystem.messagesystem.model.MessageType;

/*
 * Created by sgolitsyn on 12/8/19
 */
@Configuration
public class MSClientConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Autowired
    private DBServiceClient dbServiceClient;

    @Bean
    public MessageSystem msClient() {
        MessageSystem messageSystem = new MessageSystemImpl();

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceClient));
        messageSystem.addClient(databaseMsClient);


        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(new FrontendServiceResponceImpl()));
        messageSystem.addClient(frontendMsClient);
        messageSystem.start();
        return messageSystem;
    }
}
