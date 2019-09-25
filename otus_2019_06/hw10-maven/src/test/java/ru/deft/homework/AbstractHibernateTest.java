package ru.deft.homework;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.hibernate.HibernateUtils;

import java.util.List;

public class AbstractHibernateTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "test_hibernate.cfg.xml";

    protected static final String FIELD_ID = "id";
    protected static final String FIELD_NAME = "name";
    protected static final String TEST_USER_NAME = "Вася";
    protected static final String TEST_USER_NEW_NAME = "НЕ Вася";


    protected SessionFactory sessionFactory;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class, Address.class, Phone.class);
    }

    protected User buildDefaultUser() {
        return new User(){{setName(TEST_USER_NAME);}};
    }

    protected void saveUser(User user) {
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
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        }
    }

    protected User loadUserWithPhones(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            Hibernate.initialize(user.getPhones());
            return user;
        }
    }

    protected EntityStatistics getUserStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(User.class.getName());
    }
}
