package ru.deft.homework.api.dao;

import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface CommonDao<T> {

    long save(T user);

    void update(T user);

    void createOrUpdate(T user); // опционально.

    Optional<T> findById(long id);

    SessionManager getSessionManager();

}
