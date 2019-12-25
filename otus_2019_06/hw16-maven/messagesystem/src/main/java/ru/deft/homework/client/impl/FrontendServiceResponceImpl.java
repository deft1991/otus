package ru.deft.homework.client.impl;

import lombok.extern.slf4j.Slf4j;
import ru.deft.homework.client.FrontendServiceResponce;
import ru.deft.homework.messagesystem.messagesystem.model.Message;
import ru.deft.homework.rmi.FrontendEcho;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Created by sgolitsyn on 12/8/19
 */
@Slf4j
public class FrontendServiceResponceImpl implements FrontendServiceResponce {

    @Override
    public void sendResponceToFrontend(Message message) {
        try {
            FrontendEcho frontendEcho = (FrontendEcho) Naming.lookup("rmi://localhost/FrontendServer");
            String text = message + "--> FrontendServiceResponceImpl.sendResponceToFrontend";
            var dataFromServer = frontendEcho.sendResponceToFrontend("from FrontendServiceResponceImpl", text);
            log.info("response from the server:{}", dataFromServer);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
