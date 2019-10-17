package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbServiceException;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class DbUserServiceImpl implements DbUserService {

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
                sessionManager.commitSession();
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

    @Override
    public void commit() {
        userDao.getSessionManager().commitSession();
    }

    @Override
    public User getByNameAndPassword(String name, String password) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User user = userDao.getByNameAndPassword(name, password);
                log.log(Level.INFO, "getById.{}" + user.toString());
                return user;
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
    public User getByName(String name) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                User user = userDao.getByName(name);
                log.log(Level.INFO, "getById.{}" + user.toString());
                return user;
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
    public List<User> findAll() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<User> users = userDao.findAll();
                users.forEach(user -> log.log(Level.INFO, "findAll.{}" + user.toString()));
                return users;
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
