package ru.deft.homework.ioc;

import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import ru.deft.homework.api.dao.MessageDao;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.model.Address;
import ru.deft.homework.api.model.Message;
import ru.deft.homework.api.model.Phone;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbMessageService;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.api.service.impl.DbMessageServiceImpl;
import ru.deft.homework.api.service.impl.DbUserServiceImpl;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.chain.Handler;
import ru.deft.homework.chain.impl.doget.AllUsersHandler;
import ru.deft.homework.chain.impl.doget.BaseUserHandler;
import ru.deft.homework.chain.impl.doget.CreateProfileHandler;
import ru.deft.homework.chain.impl.doget.EditProfileUserHandler;
import ru.deft.homework.chain.impl.dopost.CreatePostProfileHandler;
import ru.deft.homework.chain.impl.dopost.UpdateProfileHandler;
import ru.deft.homework.hibernate.HibernateUtils;
import ru.deft.homework.hibernate.dao.MessageDaoHibernate;
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
            Address.class,
            Message.class);
    private SessionManager sessionManager = new SessionManagerHibernate(sessionFactory);
    private UserDao userDao = new UserDaoHibernate(sessionManager);
    private MessageDao messageDao = new MessageDaoHibernate(sessionManager);

    private DbUserService userService = new DbUserServiceImpl(userDao);
    private DbMessageService messageService = new DbMessageServiceImpl(messageDao);

    public Gson getGson() {
        return gson;
    }

    public DbUserService getDbUserService() {
        return userService;
    }

    public DbMessageService getDbMessageService() {
        return messageService;
    }

    public TemplateProcessor getTemplateProcessor() {
        return templateProcessor;
    }

    public UserService getUserService() {
        return new UserService(this.getDbUserService());
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public Handler getDoGetUserChain() {
        Handler allUsersHandler = new AllUsersHandler(userService, templateProcessor);
        Handler editProfileUserHandler = new EditProfileUserHandler(userService, templateProcessor);
        Handler createProfileHandler = new CreateProfileHandler(templateProcessor);
        Handler baseUserHandler = new BaseUserHandler(templateProcessor);
        allUsersHandler.setNext(editProfileUserHandler);
        editProfileUserHandler.setNext(createProfileHandler);
        createProfileHandler.setNext(baseUserHandler);
        return allUsersHandler;
    }

    public Handler getDoPostUserChain() {
        Handler createProfileHandler = new CreatePostProfileHandler(userService, templateProcessor);
        Handler updateProfileHandler = new UpdateProfileHandler(userService, templateProcessor);
        createProfileHandler.setNext(updateProfileHandler);
        return createProfileHandler;
    }
}
