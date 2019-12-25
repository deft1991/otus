package ru.deft.homework.client.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import ru.deft.homework.rmi.FrontendEcho;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
public class FrontendClient {

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {

        var frontendEcho = (FrontendEcho) Naming.lookup("//localhost/FrontendServer");
        var dataFromServer = frontendEcho.sendResponceToFrontend("from FrontendClient", "message");
        log.info("response from the server:{}", dataFromServer);


    }
}
