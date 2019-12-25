package ru.deft.homework.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.deft.homework.rmi.FrontendEcho;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/*
 * Created by sgolitsyn on 12/6/19
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "ru.deft.homework.server")
public class FrontendServer extends UnicastRemoteObject implements FrontendEcho {
    private static final long serialVersionUID = 1L;

    private static final int SERVER_PORT = 8091;
    private static final int REGISTRY_PORT = 1099;


    public FrontendServer() throws RemoteException {
        super(SERVER_PORT);
    }

    @Override
    public String sendResponceToFrontend(String from, String message) throws RemoteException {
        log.info("data from {} message:{}", from, message);

        try {
            WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
            List<Transport> transports = new ArrayList<>(1);
            transports.add(new WebSocketTransport(simpleWebSocketClient));
            SockJsClient sockJsClient = new SockJsClient(transports);
            WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
            String url = "ws://localhost:8080/chat-messaging";
            StompSessionHandler sessionHandler = new CustomStompSessionHandler();
            StompSession session = stompClient.connect(url, sessionHandler).get();
            session.send("hello", "qwe");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
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
