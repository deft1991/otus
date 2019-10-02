package ru.deft.homework.api.service;

import ru.deft.homework.api.model.User;

public interface DbUserService {

    User getById(Long id);

    long save(User paramClass);

    long update(User paramClass);

    long merge(User paramClass);
}
