package ru.deft.homework.hibernate.sessionmanager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.deft.homework.api.sessionmanager.DataBaseSession;

@RequiredArgsConstructor
@Getter
public class DbSessionHibernate implements DataBaseSession {

    private final Session session;
    private final Transaction transaction;

    public DbSessionHibernate(Session session) {
        this.session = session;
        this.transaction = session.beginTransaction();
    }

    public void close() {
        if (transaction.isActive()) {
            transaction.commit();
        }
        session.close();
    }

}
