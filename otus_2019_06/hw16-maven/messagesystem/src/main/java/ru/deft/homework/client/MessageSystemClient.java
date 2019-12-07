package ru.deft.homework.client;

import lombok.extern.slf4j.Slf4j;
import ru.deft.homework.rmi.MessageSystemEcho;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
public class MessageSystemClient {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        MessageSystemEcho echoInterface = (MessageSystemEcho) Naming.lookup("rmi://localhost/MessageSystemServer");
        var dataFromServer = echoInterface.echo("hello");
        log.info("response from the server:{}", dataFromServer);
    }
}
