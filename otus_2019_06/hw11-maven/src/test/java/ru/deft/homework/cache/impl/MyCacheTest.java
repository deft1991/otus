package ru.deft.homework.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongArray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Created by sgolitsyn on 10/7/19
 */
@DisplayName("Тестирование того что мой кэш ")
class MyCacheTest extends AbstractHibernateTest{


    private SessionManager sessionManagerHibernate;
    private UserDao userDaoHibernate;

    @BeforeEach
    public void setUp() {
        super.setUp();
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @Test
    @DisplayName(" корректно удаляет значения если ссылка нулл")
    public void testCache() {
        MyCache<String, AtomicLongArray> cache = new MyCache<>();
        String s = new String("blabla");
        AtomicLongArray atomicLongArray = new AtomicLongArray(new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        cache.put(s, atomicLongArray);
        assertNotNull(cache.get(s));
        s = null;
        System.gc();
        await().atMost(10, TimeUnit.SECONDS).until(cache::isEmpty);
    }

    @Test
    @DisplayName(" корректно записываем и достаем значения из кэша")
    public void testCacheWithDb() {
        List<User> users = getUsers("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        long startTime = System.currentTimeMillis();
        users.forEach(this::saveUser);
        users.forEach(user -> loadUser(user.getId()));
        long endTime = System.currentTimeMillis();
        long rezWithCach = endTime - startTime;

        users.clear();

        users = getUsers("11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
        startTime = System.currentTimeMillis();
        users.forEach(this::saveUserWithoutCache);
        users.forEach(user -> loadUserWithoutCache(user.getId()));
        endTime = System.currentTimeMillis();
        long rezWithoutCache = endTime - startTime;


        assertThat(rezWithoutCache).isGreaterThan(rezWithCach);
    }

    @Test
    @DisplayName("  сбрасывает значения после сборки мусора")
    public void testCacheReset() {
        List<User> users = getUsers("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        long startTime = System.currentTimeMillis();
        users.forEach(this::saveUser);
        users.forEach(user -> loadUser(user.getId()));
        long endTime = System.currentTimeMillis();
        long rezWithCach = endTime - startTime;

        users.clear();

        System.gc();
        await().atMost(10, TimeUnit.SECONDS).until(this.myCache::isEmpty);
    }

    private List<User> getUsers(String... names) {
        List<User> users = new ArrayList<>();
        for (String name : names){
            users.add(new User(0L, name));
        }
        return users;
    }
}
