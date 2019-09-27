package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.BaseDao;
import ru.deft.homework.api.service.DbBaseService;
import ru.deft.homework.api.service.DbServiceException;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class DbServiceImpl<T> implements DbBaseService<T> {

    private final BaseDao baseDao;

    @Override
    public T getById(final Long id) {
        try (SessionManager sessionManager = baseDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                T byId = (T) baseDao.getById(id);
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
    public long save(final T user) {
        try (SessionManager sessionManager = baseDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = baseDao.save(user);
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

    @Override
    public long update(final T user) {
        try (SessionManager sessionManager = baseDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = baseDao.update(user);
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

    @Override
    public long merge(final T user) {
        try (SessionManager sessionManager = baseDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long byId = baseDao.merge(user);
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
