package ru.deft.homework.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.deft.homework.rmi.MessageSystemEcho;

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

    private static final int SERVER_PORT = 8082;
    private static final int REGISTRY_PORT = 1097;


    public MessageSystemServer() throws RemoteException {
        super(SERVER_PORT);
    }


    @Override
    public String echo(String data) {
        log.info("data from client:{}", data);
        return "MessageSystemServer echo:" + data;

    }

    public static void main(String[] args) throws NamingException {

        try {
//            LocateRegistry.createRegistry(REGISTRY_PORT);
//            LocateRegistry.getRegistry(REGISTRY_PORT).rebind("//localhost/DBServiceServer", new MessageSystemServer());
            Naming.rebind("//localhost/MessageSystemServer", new MessageSystemServer());
            log.info("waiting for client connection");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        SpringApplication springApplication = new SpringApplication(MessageSystemServer.class);
        springApplication.setDefaultProperties(Collections
                .singletonMap("server.port", "8082"));
        springApplication.run(args);
    }

}
