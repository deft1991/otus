package ru.deft.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Created by sgolitsyn on 10/16/19
 */
@Controller
public class MyController {

    @GetMapping(value = "/")
    public String home() {

        return "index";
    }
}
