package ru.deft.homework.hibernate.sessionmanager;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.deft.homework.api.sessionmanager.DataBaseSession;
import ru.deft.homework.api.sessionmanager.SessionManager;
import ru.deft.homework.api.sessionmanager.SessionManagerException;

import java.util.logging.Level;

@Log @RequiredArgsConstructor public class SessionManagerHibernate implements SessionManager {

    private DbSessionHibernate dataBaseSession;
    private final SessionFactory sessionFactory;

    @Override public void beginSession() {
        try {
            dataBaseSession = new DbSessionHibernate(sessionFactory.openSession());
        } catch (HibernateException e) {
            log.log(Level.FINE, "SessionManagerHibernate.beginSession err {}" + e.getMessage());
            throw new SessionManagerException(e);
        }
    }

    @Override public void commitSession() {
        checkSessionAndTransaction();
        try {
            dataBaseSession.getTransaction().commit();
            dataBaseSession.getSession().close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override public void commitTransaction() {
        checkSessionAndTransaction();
        try {
            dataBaseSession.getTransaction().commit();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override public void rollbackSession() {
        checkSessionAndTransaction();
        try {
            dataBaseSession.getTransaction().rollback();
            dataBaseSession.getSession().close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override public void close() {
        if (dataBaseSession == null) {
            return;
        }
        Session session = dataBaseSession.getSession();
        if (session == null || !session.isConnected()) {
            return;
        }

        Transaction transaction = dataBaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            dataBaseSession.close();
            dataBaseSession = null;
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override public DataBaseSession getCurrentSession() {
        checkSessionAndTransaction();
        return dataBaseSession;
    }

    private void checkSessionAndTransaction() {
        if (dataBaseSession == null) {
            throw new SessionManagerException("dataBaseSession not opened ");
        }
        Session session = dataBaseSession.getSession();
        if (session == null || !session.isConnected()) {
            throw new SessionManagerException("Session not opened ");
        }

        Transaction transaction = dataBaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            throw new SessionManagerException("Transaction not opened ");
        }
    }
}
