package ru.deft.homework.hibernate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.Session;
import ru.deft.homework.api.dao.DaoExceprion;
import ru.deft.homework.api.dao.MessageDao;
import ru.deft.homework.api.model.Message;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.hibernate.sessionmanager.DbSessionHibernate;

import java.util.UUID;
import java.util.logging.Level;

/*
 * Created by sgolitsyn on 11/25/19
 */
@Log
@RequiredArgsConstructor
public class MessageDaoHibernate implements MessageDao {


    private final SessionManager sessionManager;

    @Override
    public Message getById(Long id) {
        DbSessionHibernate session = (DbSessionHibernate) sessionManager.getCurrentSession();
        try {
            return session.getSession().find(Message.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UUID save(Message message) {
        DbSessionHibernate session = (DbSessionHibernate) sessionManager.getCurrentSession();
        try {
            Session hibernateSession = session.getSession();
            if (message.getId() == null) {
                message.setId(null);
                hibernateSession.persist(message);
            } else {
                hibernateSession.merge(message);
            }
            return message.getId();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            throw new DaoExceprion(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

}
