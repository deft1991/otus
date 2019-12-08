package ru.deft.homework.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Created by sgolitsyn on 12/7/19
 */
public interface DBServiceEcho extends Remote {

    String requestToDBService(String from, String message) throws RemoteException;

}
