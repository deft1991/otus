package ru.deft.homework.api.service;

public class DBServiceException extends RuntimeException {
    public DBServiceException(Exception e) {
        super(e);
    }
}
