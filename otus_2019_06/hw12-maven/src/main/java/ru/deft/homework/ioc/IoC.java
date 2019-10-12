package ru.deft.homework.ioc;

import com.google.gson.Gson;
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
import ru.deft.homework.processor.TemplateProcessor;
import ru.deft.homework.service.UserService;

/*
 * Created by sgolitsyn on 10/7/19
 */
public class IoC {

    private static final Gson gson = new Gson();
    private static TemplateProcessor templateProcessor = new TemplateProcessor();

    private SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
            User.class,
            Phone.class,
            Address.class);
    private SessionManager sessionManager = new SessionManagerHibernate(sessionFactory);
    private UserDao userDao = new UserDaoHibernate(sessionManager);

    private DbUserService userService = new DbUserServiceImpl(userDao);

    public Gson getGson() {
        return gson;
    }

    public DbUserService getDbUserService() {
        return userService;
    }

    public TemplateProcessor getTemplateProcessor(){
        return templateProcessor;
    }

    public UserService getUserService(){
        return new UserService(this.getDbUserService());
    }

    public UserDao getUserDao(){
        return userDao;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
