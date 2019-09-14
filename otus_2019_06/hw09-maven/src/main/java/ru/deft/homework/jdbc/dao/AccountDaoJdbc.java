package ru.deft.homework.jdbc.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.dao.DaoException;
import ru.deft.homework.api.model.Account;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.jdbc.DBExecutor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log @RequiredArgsConstructor public class AccountDaoJdbc implements CommonDao<Account> {

    @Getter private final SessionManager sessionManager;
    private final DBExecutor<Account> executor;

    @Override public long save(Account account) {
        try {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(account.getId()));
            list.add(account.getType());
            list.add(String.valueOf(account.getRest()));
            return executor.insertRecord(getConnection(), "insert into Account(id, type, rest) values (?,?,?)", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public void update(Account user) {

    }

    @Override public void createOrUpdate(Account user) {

    }

    @Override public Optional<Account> findById(long id) {
        return Optional.empty();
    }

    @Override public SessionManager getSessionManager() {
        return null;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
