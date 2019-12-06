package ru.deft.homework;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.service.impl.DbUserServiceImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.cache.impl.MyCache;
import ru.deft.homework.decorator.DbServiceDecoratorWithSlowData;
import ru.deft.homework.decorator.DbServiceImplDecoratorWithCache;
import ru.deft.homework.hibernate.HibernateUtils;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongArray;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 * Created by sgolitsyn on 10/12/19
 */
@DisplayName("Тестирование того что DbServiceImpl работает быстрее с кэшом ")
class DbServiceImplDecoratorWithCacheTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "test_hibernate.cfg.xml";

    private DbUserService dbServiceDecoratorWithSlowData;
    private DbUserService dbUserServiceWithCache;

    private SessionManager sessionManagerHibernate;
    private SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Address.class, Phone.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManagerHibernate);
        DbUserService dbService = new DbUserServiceImpl(userDao);
        dbServiceDecoratorWithSlowData = new DbServiceDecoratorWithSlowData(dbService);
        dbUserServiceWithCache = new DbServiceImplDecoratorWithCache(dbService);
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
        users.forEach(user -> dbUserServiceWithCache.save(user));
        users.forEach(user -> dbUserServiceWithCache.getById(user.getId()));

        long endTime = System.currentTimeMillis();
        long rezWithCach = endTime - startTime;

        users.clear();

        users = getUsers("11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
        startTime = System.currentTimeMillis();
        users.forEach(user -> dbServiceDecoratorWithSlowData.save(user));
        users.forEach(user -> dbServiceDecoratorWithSlowData.getById(user.getId()));
        endTime = System.currentTimeMillis();
        long rezWithoutCache = endTime - startTime;


        assertThat(rezWithoutCache).isGreaterThan(rezWithCach);
    }

    private List<User> getUsers(String... names) {
        List<User> users = new ArrayList<>();
        for (String name : names) {
            users.add(new User(0L, name));
        }
        return users;
    }
}
