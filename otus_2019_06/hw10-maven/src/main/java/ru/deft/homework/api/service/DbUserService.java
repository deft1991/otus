package ru.deft.homework.api.service;

import ru.deft.homework.api.model.User;

import java.util.List;

public interface DbUserService {

    User getById(Long id);

    long save(User paramClass);

    long update(User paramClass);

    long merge(User paramClass);

    void commit();

    User getByNameAndPassword(String name, String password);

    User getByName(String name);

    List<User> findAll();
}
