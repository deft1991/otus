package ru.deft.homework;

import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.impl.DbServiceImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.HibernateUtils;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.metamodel.EntityType;

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
        hibernateInitInfo();
        SessionManager sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManagerHibernate);
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

    private static void hibernateInitInfo() {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }
}
