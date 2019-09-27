package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.service.DbServiceException;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class DbServiceImpl implements DbUserService {

    private final UserDao userDao;

    public User getById(final Long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User byId = (User) userDao.getById(id);
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

    // todo parametrize function and set needed consumer
    @Override
    public long save(final User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = userDao.save(user);
                log.log(Level.INFO, "save.{}" + byId);
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                sessionManager.rollbackSession();
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return -1L;
    }

    public long update(final User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = userDao.update(user);
                log.log(Level.INFO, "update.{}" + byId);
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                sessionManager.rollbackSession();
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return -1L;
    }

    public long merge(final User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = userDao.merge(user);
                log.log(Level.INFO, "merge.{}" + byId);
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                sessionManager.rollbackSession();
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return -1L;
    }
}
