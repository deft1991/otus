package ru.deft.homework.controller;

/*
 * Created by sgolitsyn on 10/15/19
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.deft.homework.api.model.User;
import ru.deft.homework.service.LoginService;

import javax.servlet.http.HttpSession;

@Log
@Controller
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessge = null;
        if(error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login";
    }


    @GetMapping(value = "/loginFailed")
    public String loginError(Model model) {
        log.info("Login attempt failed");
        model.addAttribute("error", "true");
        return "login";
    }

    @PostMapping
    public String authenticate(Model model, @RequestParam String name, @RequestParam String password) {

        User authenticate = loginService.authenticate(name);
        model.addAttribute("user", authenticate);
        return "redirect:/user/all";
    }
}
