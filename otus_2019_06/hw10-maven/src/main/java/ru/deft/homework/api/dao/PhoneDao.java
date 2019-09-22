package ru.deft.homework.api.dao;

import java.util.List;

/*
 * Created by sgolitsyn on 9/22/19
 */
public interface PhoneDao<T> extends UserDao<T> {

    List<T> findByUserId(long id);
}
