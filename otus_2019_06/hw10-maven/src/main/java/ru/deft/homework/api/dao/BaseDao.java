package ru.deft.homework.api.dao;

import ru.deft.homework.api.sessionmanager.SessionManager;

public interface BaseDao<T> {

    T getById(Long id);

    long save(T user);

    long update(T user);

    long merge(T user);

    SessionManager getSessionManager();
}
