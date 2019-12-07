package ru.deft.homework.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.deft.homework.rmi.FrontendEcho;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "ru.deft.homework.server")
public class FrontendServer extends UnicastRemoteObject implements FrontendEcho {
    private static final long serialVersionUID = 1L;

    private static final int SERVER_PORT = 8090;
    private static final int REGISTRY_PORT = 1099;


    public FrontendServer() throws RemoteException {
        super(SERVER_PORT);
    }


    @Override
    public String echo(String data) {
        log.info("data from client:{}", data);
        return "FrontendServer echo:" + data;

    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FrontendServer.class);
        springApplication.setDefaultProperties(Collections
                .singletonMap("server.port", "8081"));
        springApplication.run(args);
        try {
            LocateRegistry.createRegistry(REGISTRY_PORT);

            Naming.rebind("//localhost/FrontendServer", new FrontendServer());
            log.info("waiting for client connection");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
