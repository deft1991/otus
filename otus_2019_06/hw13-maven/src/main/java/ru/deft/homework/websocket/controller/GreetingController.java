package ru.deft.homework.websocket.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/*
 * Created by sgolitsyn on 10/16/19
 */
@Log
@Controller
public class GreetingController {


    @GetMapping(value = "/")
    public String home() {
        return "index";
    }

    @GetMapping(value = "/webSocket")
    public String webSocket() {
        return "webSocket";
    }
}
