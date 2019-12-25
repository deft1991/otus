//package ru.deft.homework.client;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import ru.deft.homework.rmi.DBServiceEcho;
//
//import java.net.MalformedURLException;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//
///*
// * Created by sgolitsyn on 12/6/19
// */
//@Slf4j
//public class DBServiceClient {
//
//    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
//        DBServiceEcho echoInterface = (DBServiceEcho) Naming.lookup("rmi://localhost/DBServiceServer");
//        var dataFromServer = echoInterface.requestToDBService("from DBServiceClient", "message");
//        log.info("response from the server:{}", dataFromServer);
//    }
//
//    @Bean
//    ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }
//}
