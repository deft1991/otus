package ru.deft.auth.controller;

/*
 * Created by sgolitsyn on 12/16/19
 */

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.deft.auth.model.UserEntity;
import ru.deft.auth.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public Principal user(Principal principal) {
        return principal;
    }


    @PostMapping("/create")
    public Long user(UserEntity userEntity) {
        return userService.createUser(userEntity);
    }
}
