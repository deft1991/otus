package ru.deft.homework.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.deft.homework.websocket"})
@EntityScan("ru.deft.homework.websocket")
public class FrontendWebSocket {

    public static void main(String[] args) {
        SpringApplication.run(FrontendWebSocket.class, args);
    }
}
