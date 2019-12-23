package ru.deft.auth.service;

import ru.deft.auth.model.UserEntity;

/*
 * Created by sgolitsyn on 12/23/19
 */
public interface UserService {

    Long createUser(UserEntity user);
}
