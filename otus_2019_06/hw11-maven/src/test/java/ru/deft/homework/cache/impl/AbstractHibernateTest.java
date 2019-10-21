package ru.deft.homework.cache.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.cache.HwListener;
import ru.deft.homework.hibernate.HibernateUtils;

public class AbstractHibernateTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "test_hibernate.cfg.xml";

    protected static final String FIELD_ID = "id";
    protected static final String FIELD_NAME = "name";
    protected static final String TEST_USER_NAME = "Вася";
    protected static final String TEST_USER_NEW_NAME = "НЕ Вася";


    protected SessionFactory sessionFactory;
    protected MyCache<Long, User> myCache;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Address.class, Phone.class);
        myCache = new MyCache<>();
        HwListener hwListener =
                (key, value, action) ->
                        System.out.println(String.format("key = %d, value = %s, action = %s ", key, value, action));
        myCache.addListener(hwListener);
    }

    protected User buildDefaultUser() {
        return new User() {{
            setName(TEST_USER_NAME);
        }};
    }

    protected void saveUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            saveUser(session, user);
            myCache.put(new Long(user.getId()), user);
        }
    }


    protected void saveUserWithoutCache(User user) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try (Session session = sessionFactory.openSession()) {
            saveUser(session, user);
        }
    }

    protected void saveUser(Session session, User user) {
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }


    protected User loadUser(long id) {
        if (myCache.get(id) != null) {
            return myCache.get(id);
        }
        try (Session session = sessionFactory.openSession()) {
            Thread.sleep(100);
            return session.find(User.class, id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected User loadUserWithoutCache(long id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        }
    }

    protected User loadUserWithPhones(long id) {
        if (myCache.get(id) != null) {
            return myCache.get(id);
        }
        try (Session session = sessionFactory.openSession()) {
            Thread.sleep(100);
            User user = session.find(User.class, id);
            Hibernate.initialize(user.getPhones());
            return user;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected EntityStatistics getUserStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(User.class.getName());
    }
}
