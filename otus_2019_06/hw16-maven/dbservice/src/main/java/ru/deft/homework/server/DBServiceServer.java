package ru.deft.homework.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.deft.homework.rmi.DBServiceEcho;

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
public class DBServiceServer extends UnicastRemoteObject implements DBServiceEcho {
    private static final long serialVersionUID = 1L;

    private static final int SERVER_PORT = 8082;

    public DBServiceServer() throws RemoteException {
        super(8092);
    }

    @Override
    public String requestToDBService(String from, String message) throws RemoteException {
        // todo add save to data base or some logic
        log.info("data from {} client:{}", from, message);
        return message + "--> From DBServiceServer.requestToDBService";
    }

    public static void main(String[] args) throws NamingException, RemoteException {
        try {
            Naming.rebind("//localhost/DBServiceServer", new DBServiceServer());
            log.info("waiting for client connection");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        SpringApplication springApplication = new SpringApplication(DBServiceServer.class);
        springApplication.setDefaultProperties(Collections
                .singletonMap("server.port", SERVER_PORT));
        springApplication.run(args);
    }

}
