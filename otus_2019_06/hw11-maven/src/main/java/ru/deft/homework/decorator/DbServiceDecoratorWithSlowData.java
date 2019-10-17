package ru.deft.homework.decorator;

import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;

/*
 * Created by sgolitsyn on 10/12/19
 */
public class DbServiceDecoratorWithSlowData extends BaseDecorator {

    public DbServiceDecoratorWithSlowData(DbUserService dbService) {
        super(dbService);
    }

    @Override
    public User getById(Long id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.getById(id);
    }

    @Override
    public long save(User paramClass) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return super.save(paramClass);
    }
}
