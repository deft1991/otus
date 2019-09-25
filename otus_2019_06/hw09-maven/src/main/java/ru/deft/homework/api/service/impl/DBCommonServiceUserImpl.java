package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DBCommonService;
import ru.deft.homework.api.service.DBServiceException;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.Optional;

@Log @RequiredArgsConstructor public class DBCommonServiceUserImpl implements DBCommonService<User> {

    private final CommonDao commonDao;

    @Override public long save(User user) {
        SessionManager sessionManager = commonDao.getSessionManager();
        sessionManager.beginSession();
        try {
            long userId = commonDao.save(user);
            sessionManager.commitSession();
            log.info("created user: " + userId);
            return userId;
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public void update(User user) {
        SessionManager sessionManager = commonDao.getSessionManager();
        sessionManager.beginSession();
        try {
            commonDao.update(user);
            sessionManager.commitSession();
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public void createOrUpdate(User user) {
        SessionManager sessionManager = commonDao.getSessionManager();
        sessionManager.beginSession();
        try {
            commonDao.createOrUpdate(user);
            sessionManager.commitSession();
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public Optional<User> findById(long id) {
        try (SessionManager sessionManager = commonDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = commonDao.findById(id);

                log.info("user: " + userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                log.warning(e.getMessage());
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
