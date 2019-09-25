package ru.deft.homework.api.service;

public interface DbBaseService<T> {

    T getById(Long id);

    long save(T paramClass);

    long update(T paramClass);

    long merge(T paramClass);
}
