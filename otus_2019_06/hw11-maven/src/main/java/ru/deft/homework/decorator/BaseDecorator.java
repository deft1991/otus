package ru.deft.homework.decorator;

import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;

import java.util.List;

/*
 * Created by sgolitsyn on 10/12/19
 */
public class BaseDecorator implements DbUserService {
    protected final DbUserService dbService;

    public BaseDecorator(DbUserService dbService) {
        this.dbService = dbService;
    }

    @Override
    public User getById(Long id) {
        return dbService.getById(id);
    }

    @Override
    public long save(User paramClass) {
        return dbService.save(paramClass);
    }

    @Override
    public long update(User paramClass) {
        return dbService.update(paramClass);
    }

    @Override
    public long merge(User paramClass) {
        return dbService.merge(paramClass);
    }

    @Override
    public void commit() {

    }

    @Override
    public User getByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public User getByName(String name) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
