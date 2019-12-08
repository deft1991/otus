package ru.deft.homework.websocket.client;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;
import ru.deft.homework.rmi.MessageSystemEcho;
import ru.deft.homework.websocket.domain.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
@Component
public class MessageSystemClient implements IMessageSystem {

    public void sendRequestToMessageSystem(Message message) {
        MessageSystemEcho echoInterface = null;
        var dataFromServer = "";
        try {
            echoInterface = (MessageSystemEcho) Naming.lookup("rmi://localhost/MessageSystemServer");
            dataFromServer = echoInterface.requestToMessageSystem("databaseService", "From MessageSystemClient:" + message.getMessage());
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
        log.info("response from the server:{}", dataFromServer);
    }
}
