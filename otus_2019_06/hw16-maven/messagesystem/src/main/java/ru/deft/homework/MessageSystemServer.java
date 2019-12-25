package ru.deft.homework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.deft.homework.client.FrontendServiceResponce;
import ru.deft.homework.client.impl.FrontendServiceResponceImpl;
import ru.deft.homework.messagesystem.db.handlers.GetUserDataRequestHandler;
import ru.deft.homework.messagesystem.front.FrontendService;
import ru.deft.homework.messagesystem.front.handlers.GetUserDataResponseHandler;
import ru.deft.homework.messagesystem.front.impl.FrontendServiceImpl;
import ru.deft.homework.messagesystem.messagesystem.MessageSystem;
import ru.deft.homework.messagesystem.messagesystem.MsClient;
import ru.deft.homework.messagesystem.messagesystem.impl.MessageSystemImpl;
import ru.deft.homework.messagesystem.messagesystem.impl.MsClientImpl;
import ru.deft.homework.messagesystem.messagesystem.model.Message;
import ru.deft.homework.messagesystem.messagesystem.model.MessageType;
import ru.deft.homework.rmi.MessageSystemEcho;
import ru.deft.homework.client.impl.DBServiceClientImpl;

import javax.naming.NamingException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
@SpringBootApplication
public class MessageSystemServer extends UnicastRemoteObject implements MessageSystemEcho {
    private static final long serialVersionUID = 1L;

    private static final int SERVER_PORT = 8083;

    private final MessageSystem msClient;
    public MessageSystemServer() throws RemoteException {
        super(SERVER_PORT);
        msClient = msClient();
        msClient.start();
    }

    @Override
    public String requestToMessageSystem(String from, String message) {
        log.info("data from {} message:{}", from, message);
        msClient.newMessage(new Message(from, message));

        return "MessageSystemServer echo:" + from;

    }

    public static void main(String[] args) throws NamingException {

        try {
            Naming.rebind("//localhost/MessageSystemServer", new MessageSystemServer());
            log.info("waiting for client connection");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        SpringApplication springApplication = new SpringApplication(MessageSystemServer.class);
        springApplication.setDefaultProperties(Collections
                .singletonMap("server.port", SERVER_PORT));
        springApplication.run(args);
    }

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";


    public MessageSystem msClient() {
        MessageSystem messageSystem = new MessageSystemImpl();

        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem);
        DBServiceClientImpl dbService = new DBServiceClientImpl();
        databaseMsClient.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbService));
        messageSystem.addClient(databaseMsClient);


        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, messageSystem);
        frontendMsClient.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(new FrontendServiceResponceImpl()));
        messageSystem.addClient(frontendMsClient);
        return messageSystem;
    }
}
