package ru.deft.homework;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.service.impl.DbUserServiceImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.HibernateUtils;
import ru.deft.homework.hibernate.dao.UserDaoHibernate;
import ru.deft.homework.hibernate.sessionmanager.SessionManagerHibernate;

@Log
public class Main {


    public static void main(final String[] args) {
        SessionFactory sessionFactory =
                HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                        User.class,
                        Phone.class,
                        Address.class);
        SessionManager sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManagerHibernate);
        DbUserService dbService = new DbUserServiceImpl(userDao);

        long id = dbService.save(new User(0L, "Вася"));
        User mayBeCreatedUser = dbService.getById(id);
        System.out.println(mayBeCreatedUser.getName());

        Address address = new Address();
        address.setStreet("street it");
        mayBeCreatedUser.setAddress(address);
        dbService.update(mayBeCreatedUser);
        mayBeCreatedUser = (User) dbService.getById(id);
        System.out.println("after add addresses: " + mayBeCreatedUser);

    }
}
