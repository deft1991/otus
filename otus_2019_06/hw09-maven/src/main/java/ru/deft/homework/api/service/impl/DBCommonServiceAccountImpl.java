package ru.deft.homework.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.model.Account;
import ru.deft.homework.api.service.DBCommonService;
import ru.deft.homework.api.service.DBServiceException;
import ru.deft.homework.api.sessionmanager.SessionManager;

import java.util.Optional;

@Log @RequiredArgsConstructor public class DBCommonServiceAccountImpl implements DBCommonService<Account> {

    private final CommonDao commonDao;

    @Override public long save(Account account) {
        SessionManager sessionManager = commonDao.getSessionManager();
        sessionManager.beginSession();
        try {
            long accountId = commonDao.save(account);
            sessionManager.commitSession();
            log.info("created account: " + accountId);
            return accountId;
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public void update(Account account) {
        SessionManager sessionManager = commonDao.getSessionManager();
        sessionManager.beginSession();
        try {
            commonDao.update(account);
            sessionManager.commitSession();
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public void createOrUpdate(Account account) {
        SessionManager sessionManager = commonDao.getSessionManager();
        sessionManager.beginSession();
        try {
            commonDao.createOrUpdate(account);
            sessionManager.commitSession();
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public Optional<Account> findById(long id) {
        try (SessionManager sessionManager = commonDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = commonDao.findById(id);

                log.info("account: " + accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                log.warning(e.getMessage());
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
