package ru.deft.homework.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.deft.homework.websocket")
public class FrontendWebSocket {

    public static void main(String[] args) {
        SpringApplication.run(FrontendWebSocket.class, args);
    }
}
