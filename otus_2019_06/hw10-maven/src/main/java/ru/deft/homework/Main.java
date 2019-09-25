package ru.deft.homework;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.deft.homework.api.dao.BaseDao;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.impl.DbServiceImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.HibernateUtils;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;

@Log public class Main {

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) {
        SessionFactory sessionFactory =
                HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                        User.class,
                        Phone.class,
                        Address.class);
        SessionManager sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        BaseDao userDao = new UserDaoHibernate(sessionManagerHibernate);
        DbServiceImpl dbService = new DbServiceImpl(userDao);

        long id = dbService.save(new User(0L, "Вася"));
        User mayBeCreatedUser = (User) dbService.getById(id);
        System.out.println(mayBeCreatedUser.getName());

        Address address = new Address();
        address.setStreet("street it");
        mayBeCreatedUser.setAddress(address);
        dbService.update(mayBeCreatedUser);
        mayBeCreatedUser = (User) dbService.getById(id);
        System.out.println("after add addresses: " + mayBeCreatedUser);

    }
}
