package ru.deft.homework.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.deft.homework.client.DBServiceClient;
import ru.deft.homework.messagesystem.messagesystem.model.Message;
import ru.deft.homework.rmi.DBServiceEcho;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
@Component
public class DBServiceClientImpl implements DBServiceClient {

    public Message saveMessageInDbRequest(Message message) {
        DBServiceEcho dbServiceEcho = null;
        String dataFromServer = null;
        try {
            dbServiceEcho = (DBServiceEcho) Naming.lookup("rmi://localhost/DBServiceServer");
            dataFromServer = dbServiceEcho.requestToDBService(message.getFrom(), message.getMessage());
            log.info("response from the server:{}", dataFromServer);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
        return new Message("frontendService", dataFromServer + "--> DBServiceClientImpl.saveMessageInDbRequest");
    }

}
