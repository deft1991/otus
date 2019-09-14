package ru.deft.homework.jdbc.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.dao.DaoException;
import ru.deft.homework.api.model.Account;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.jdbc.DbExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log @RequiredArgsConstructor public class AccountDaoJdbc implements CommonDao<Account> {

    @Getter private final SessionManager sessionManager;
    private final DbExecutor<Account> executor;

    @Override public long save(Account account) {
        try {
            List<String> list = new ArrayList<>();
            list.add(account.getType());
            list.add(String.valueOf(account.getRest()));
            return executor.insertRecord(getConnection(), "insert into Account(type, rest) values (?,?)", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public void update(Account account) {
        try {
            List<String> list = new ArrayList<>();
            list.add(account.getType());
            list.add(String.valueOf(account.getRest()));
            list.add(String.valueOf(account.getId()));
            executor.updateRecord(getConnection(), "update account set type =? , rest = ? where id = ?", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public void createOrUpdate(Account account) {
        try {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(account.getId()));
            list.add(account.getType());
            list.add(String.valueOf(account.getRest()));
            executor.updateRecord(getConnection(), "MERGE INTO Account KEY (ID) VALUES (?, ?, ?)", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public Optional<Account> findById(long id) {
        try {
            return executor.selectRecord(getConnection(), "select id, type, rest from Account where id  = ?", id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new Account(resultSet.getLong("id"), resultSet.getString("type"), resultSet.getInt("rest"));
                    }
                } catch (SQLException e) {
                    log.warning(e.getMessage());
                }
                return null;
            });
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return Optional.empty();
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

}
