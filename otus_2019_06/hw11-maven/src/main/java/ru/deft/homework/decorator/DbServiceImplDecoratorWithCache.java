package ru.deft.homework.decorator;

import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.service.impl.DbServiceImpl;
import ru.deft.homework.cache.HwListener;
import ru.deft.homework.cache.impl.MyCache;

/*
 * Created by sgolitsyn on 10/12/19
 */
public class DbServiceImplDecoratorWithCache extends BaseDecorator {
    private MyCache<Long, User> myCache;

    public DbServiceImplDecoratorWithCache(DbUserService dbService) {
        super(dbService);
        myCache = new MyCache<>();
        HwListener hwListener =
                (key, value, action) ->
                        System.out.println(String.format("key = %d, value = %s, action = %s ", key, value, action));
        myCache.addListener(hwListener);
    }

    public DbServiceImplDecoratorWithCache(DbServiceImpl dbService, MyCache<Long, User> myCache) {
        super(dbService);
        this.myCache = myCache;
    }

    public User getById(final Long id) {
        if (myCache.get(id) != null) {
            return myCache.get(id);
        }
        return dbService.getById(id);
    }

    public long save(final User user) {
        dbService.save(user);
        myCache.put(new Long(user.getId()), user);
        return user.getId();
    }

    public long update(final User user) {
        dbService.update(user);
        myCache.put(new Long(user.getId()), user);
        return user.getId();
    }

    public long merge(final User user) {
        dbService.merge(user);
        myCache.put(new Long(user.getId()), user);
        return user.getId();
    }
}
