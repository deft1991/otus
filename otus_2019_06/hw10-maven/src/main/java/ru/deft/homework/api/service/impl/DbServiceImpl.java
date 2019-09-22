package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.service.DbServiceException;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.logging.Level;

@Log @RequiredArgsConstructor public class DbServiceImpl<T> implements DbUserService<T> {

    private final UserDao userDao;

    @Override public T getById(Long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                T byId = (T) userDao.getById(id);
                log.log(Level.INFO, "getById.{}" + byId.toString());
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return null;
    }

    @Override public long save(T user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = userDao.save(user);
                log.log(Level.INFO, "save.{}" + byId);
                return byId;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        } catch (Exception e) {
            throw new DbServiceException(e);
        }
        return -1L;
    }

    @Override public long update(T user) {
        return 0;
    }

    @Override public long merge(T user) {
        return 0;
    }
}
