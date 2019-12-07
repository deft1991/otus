package ru.deft.homework.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.deft.homework.rmi.DBServiceEcho;

import javax.naming.NamingException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
@SpringBootApplication
public class DBServiceServer extends UnicastRemoteObject implements DBServiceEcho {
    private static final long serialVersionUID = 1L;

    private static final int SERVER_PORT = 8083;
    private static final int REGISTRY_PORT = 1099;


    public DBServiceServer() throws RemoteException {
        super(SERVER_PORT);
    }


    @Override
    public String echo(String data) {
        log.info("data from client:{}", data);
        return "DBServiceServer echo:" + data;

    }

    public static void main(String[] args) throws NamingException {

        try {
            Naming.rebind("//localhost/DBServiceServer", new DBServiceServer());
            log.info("waiting for client connection");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        SpringApplication springApplication = new SpringApplication(DBServiceServer.class);
        springApplication.setDefaultProperties(Collections
                .singletonMap("server.port", "8082"));
        springApplication.run(args);
    }

}
