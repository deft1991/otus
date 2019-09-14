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
            return commonDao.save(account);
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            sessionManager.rollbackSession();
            throw new DBServiceException(e);
        }
    }

    @Override public void update(Account user) {

    }

    @Override public void createOrUpdate(Account user) {

    }

    @Override public Optional<Account> findById(long id) {
        return null;
    }
}
