package ru.deft.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.deft.homework.db.DBService;
import ru.deft.homework.db.handlers.GetUserDataRequestHandler;
import ru.deft.homework.db.impl.DBServiceImpl;
import ru.deft.homework.front.FrontendService;
import ru.deft.homework.front.handlers.GetUserDataResponseHandler;
import ru.deft.homework.front.impl.FrontendServiceImpl;
import ru.deft.homework.messagesystem.MessageSystem;
import ru.deft.homework.messagesystem.MsClient;
import ru.deft.homework.messagesystem.impl.MessageSystemImpl;
import ru.deft.homework.messagesystem.impl.MsClientImpl;
import ru.deft.homework.messagesystem.model.MessageType;


/*
 * Created by sgolitsyn on 11/15/19
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public static void main(String[] args) throws InterruptedException {
        MessageSystem messageSystem = new MessageSystemImpl();

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        DBService dbService = new DBServiceImpl();
        databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbService));
        messageSystem.addClient(databaseMsClient);


        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        FrontendService frontendService = new FrontendServiceImpl(frontendMsClient, DATABASE_SERVICE_CLIENT_NAME);
        frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(frontendService));
        messageSystem.addClient(frontendMsClient);
        messageSystem.start();
        frontendService.getUserData(1, data -> logger.info("got data:{}", data));

        Thread.sleep(10000);
        messageSystem.dispose();
        logger.info("done");
    }
}
