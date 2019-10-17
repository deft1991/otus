package ru.deft.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.deft.homework.api.model.User;
import ru.deft.homework.service.LoginService;
import ru.deft.homework.service.UserService;

/*
 * Created by sgolitsyn on 10/16/19
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserService userService;

    public User authenticate(String name) {
        return userService.getByName(name);
    }
}
