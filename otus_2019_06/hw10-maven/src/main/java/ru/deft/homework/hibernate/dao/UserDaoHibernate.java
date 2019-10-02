package ru.deft.homework.hibernate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.Session;
import ru.deft.homework.api.dao.UserDao;
import ru.deft.homework.api.dao.DaoExceprion;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.sessionmanager.DbSessionHibernate;

import java.util.logging.Level;

@Log
@RequiredArgsConstructor
public class UserDaoHibernate implements UserDao {

    private final SessionManager sessionManager;

    @Override
    public User getById(Long id) {
        DbSessionHibernate session = (DbSessionHibernate) sessionManager.getCurrentSession();
        try {
            return session.getSession().find(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long save(User user) {
        DbSessionHibernate session = (DbSessionHibernate) sessionManager.getCurrentSession();
        try {
            Session hibernateSession = session.getSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                user.setId(null);
                hibernateSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            throw new DaoExceprion(e);
        }
    }

    @Override
    public long update(User user) {
        DbSessionHibernate session = (DbSessionHibernate) sessionManager.getCurrentSession();
        Session hibernateSession = session.getSession();
        hibernateSession.update(user);
        return user.getId();
    }

    @Override
    public long merge(User user) {
        DbSessionHibernate session = (DbSessionHibernate) sessionManager.getCurrentSession();
        Session hibernateSession = session.getSession();
        hibernateSession.merge(user);
        return user.getId();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
