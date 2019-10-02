package ru.deft.homework.api.dao;

import ru.deft.homework.api.model.User;
import ru.deft.homework.api.sessionmanager.SessionManager;

public interface UserDao {

    User getById(Long id);

    long save(User user);

    long update(User user);

    long merge(User user);

    SessionManager getSessionManager();
}
