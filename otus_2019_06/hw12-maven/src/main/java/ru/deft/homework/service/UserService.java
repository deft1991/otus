package ru.deft.homework.service;

import lombok.RequiredArgsConstructor;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;

/*
 * Created by sgolitsyn on 10/9/19
 */
@RequiredArgsConstructor
public class UserService {

    private final DbUserService dbUserService;

    public boolean authenticate(String name, String password) {
        User user = dbUserService.getByNameAndPassword(name, password);
        if (user != null){
            return true;
        }
        return false;
    }
}
