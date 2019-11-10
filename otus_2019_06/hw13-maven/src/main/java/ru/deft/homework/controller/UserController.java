package ru.deft.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.deft.homework.api.model.User;
import ru.deft.homework.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/14/19
 */
@Log
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public String getAll(Model model) {
        log.info("in UserServlet -- getAll ");
        List<User> users = userService.findAll();
        Map<String, Object> pageParams = new HashMap<>();
        pageParams.put("users", users);
        model.addAllAttributes(pageParams);
        return "usersAll";
    }

    @GetMapping("/create_profile")
    public String getCreateProfile(Model model) {
        log.info("in UserServlet -- getCreateProfile ");
        return "userSave";
    }

    @GetMapping("/edit_profile")
    public String getEditProfile(Model model, @RequestParam String userId) {
        log.info("in UserServlet -- getEditProfile ");
        User user = userService.getById(Long.valueOf(userId));
        Map<String, Object> pageParams = new HashMap<>();
        pageParams.put("user", user);
        model.addAllAttributes(pageParams);
        return "usersEdit";
    }

    @PostMapping("/create_profile")
    public String createProfile(Model model, @RequestParam String name, @RequestParam String password) {
        User user = new User(name, password);
        return getPageWithParams(model, user);
    }

    @PostMapping("/update_profile")
    public String updateProfile(Model model, @RequestParam String userId, @RequestParam String name, @RequestParam String password) {
        User user = userService.getById(Long.valueOf(userId));
        user.setName(name);
        user.setPassword(password);
        return getPageWithParams(model, user);
    }

    private String getPageWithParams(Model model, User user) {
        userService.save(user);
        List<User> users = userService.findAll();
        Map<String, Object> pageParams = new HashMap<>();
        pageParams.put("users", users);
        model.addAllAttributes(pageParams);
        return "redirect:/user/all";
    }
}
