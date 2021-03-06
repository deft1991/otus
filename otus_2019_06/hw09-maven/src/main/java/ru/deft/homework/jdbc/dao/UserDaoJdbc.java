package ru.deft.homework.jdbc.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import ru.deft.homework.api.dao.CommonDao;
import ru.deft.homework.api.dao.DaoException;
import ru.deft.homework.api.model.User;
import ru.deft.homework.jdbc.DbExecutor;
import ru.deft.homework.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log @RequiredArgsConstructor public class UserDaoJdbc implements CommonDao<User> {

    @Getter private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> executor;

    @Override public long save(User user) {
        try {
            List<String> list = new ArrayList<>();
            list.add(user.getName());
            list.add(String.valueOf(user.getAge()));
            return executor.insertRecord(getConnection(), "insert into user(name, age) values (?,?)", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public void update(User user) {
        try {
            List<String> list = new ArrayList<>();
            list.add(user.getName());
            list.add(String.valueOf(user.getAge()));
            list.add(String.valueOf(user.getId()));
            executor.updateRecord(getConnection(), "update user set name =? , age = ? where id = ?", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public void createOrUpdate(User user) {
        try {
            List<String> list = new ArrayList<>();
            list.add(String.valueOf(user.getId()));
            list.add(user.getName());
            list.add(String.valueOf(user.getAge()));
            executor.updateRecord(getConnection(), "MERGE INTO user KEY (ID) VALUES (?, ?, ?)", list);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override public Optional<User> findById(long id) {
        try {
            return executor.selectRecord(getConnection(), "select id, name, age from user where id  = ?", id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
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
