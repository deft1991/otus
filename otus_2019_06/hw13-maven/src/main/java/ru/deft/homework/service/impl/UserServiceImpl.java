package ru.deft.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.deft.homework.api.model.User;
import ru.deft.homework.api.service.DbUserService;
import ru.deft.homework.service.UserService;

import java.util.List;

/*
 * Created by sgolitsyn on 10/14/19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DbUserService dbService;

    public List<User> findAll() {
        return dbService.findAll();
    }

    public User getById(Long userId) {
        return dbService.getById(userId);
    }

    @Override
    public User getByName(String name) {
        return dbService.getByName(name);
    }

    @Override
    public long save(User user) {
        return dbService.save(user);
    }
}
