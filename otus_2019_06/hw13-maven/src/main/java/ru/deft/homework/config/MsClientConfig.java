package ru.deft.homework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import ru.deft.homework.db.DBService;
import ru.deft.homework.db.handlers.GetUserDataRequestHandler;
import ru.deft.homework.db.impl.DBServiceImpl;
import ru.deft.homework.front.handlers.GetUserDataResponseHandler;
import ru.deft.homework.front.impl.FrontendServiceImpl;
import ru.deft.homework.messagesystem.MessageSystem;
import ru.deft.homework.messagesystem.MsClient;
import ru.deft.homework.messagesystem.impl.MessageSystemImpl;
import ru.deft.homework.messagesystem.impl.MsClientImpl;
import ru.deft.homework.messagesystem.model.MessageType;

/*
 * Created by sgolitsyn on 11/18/19
 */
@Configuration
@ComponentScan
@EnableSpringConfigured
public class MsClientConfig {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    private FrontendServiceImpl frontendService;

    public FrontendServiceImpl getFrontendService() {
        return frontendService;
    }

    public MsClientConfig() {
        messageSystemInit();
    }

    public MessageSystem messageSystemInit() {
        MessageSystem messageSystem = new MessageSystemImpl();

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        DBService dbService = new DBServiceImpl();
        databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbService));
        messageSystem.addClient(databaseMsClient);


        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        this.frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);
        messageSystem.start();
        return messageSystem;
    }

}
