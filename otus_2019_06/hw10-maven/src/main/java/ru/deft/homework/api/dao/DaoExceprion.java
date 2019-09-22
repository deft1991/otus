package ru.deft.homework.api.dao;

public class DaoExceprion extends RuntimeException {

    public DaoExceprion(Exception ex) {
        super(ex);
    }
}
