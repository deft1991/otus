package ru.deft.homework.api.dao;

import ru.deft.homework.api.model.Message;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.UUID;

/*
 * Created by sgolitsyn on 11/25/19
 */
public interface MessageDao {

    Message getById(Long id);

    UUID save(Message message);

    SessionManager getSessionManager();
}
