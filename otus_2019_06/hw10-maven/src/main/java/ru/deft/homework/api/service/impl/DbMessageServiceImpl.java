package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.MessageDao;
import ru.deft.homework.api.model.Message;
import ru.deft.homework.api.service.DbMessageService;
import ru.deft.homework.api.service.DbServiceException;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.UUID;
import java.util.logging.Level;

/*
 * Created by sgolitsyn on 11/25/19
 */
@Log
@RequiredArgsConstructor
public class DbMessageServiceImpl implements DbMessageService {

    private final MessageDao messageDao;

    @Override
    public Message getById(Long id) {
        try (SessionManager sessionManager = messageDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Message byId = (Message) messageDao.getById(id);
                log.log(Level.INFO, "getById.{}" + byId.toString());
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                sessionManager.rollbackSession();
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return null;
    }

    @Override
    public UUID save(Message message) {
        try (SessionManager sessionManager = messageDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                UUID byId = messageDao.save(message);
                log.log(Level.INFO, "save.{}" + byId);
                sessionManager.commitSession();
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                sessionManager.rollbackSession();
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return null;
    }
}
