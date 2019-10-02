package ru.deft.homework.api.sessionmanager;

public interface SessionManager extends AutoCloseable {

    void beginSession();

    void commitSession();

    void commitTransaction();

    void rollbackSession();

    void close();

    DataBaseSession getCurrentSession();
}
