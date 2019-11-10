package ru.deft.homework.service;

import ru.deft.homework.api.model.User;

/*
 * Created by sgolitsyn on 10/16/19
 */
public interface LoginService {
    public User authenticate(String name);
}
