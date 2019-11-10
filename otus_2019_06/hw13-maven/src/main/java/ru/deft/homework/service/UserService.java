package ru.deft.homework.service;

import ru.deft.homework.api.model.User;

import java.util.List;

/*
 * Created by sgolitsyn on 10/16/19
 */
public interface UserService {

    public List<User> findAll();
    public User getById(Long userId);

    User getByName(String name);

    long save(User user);
}
