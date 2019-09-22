package ru.deft.homework.api.service;

public interface DbUserService<T> {

    T getById(Long id);

    long save(T user);

    long update(T user);

    long merge(T user);
}
