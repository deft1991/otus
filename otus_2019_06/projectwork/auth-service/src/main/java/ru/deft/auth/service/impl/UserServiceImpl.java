package ru.deft.auth.service.impl;

/*
 * Created by sgolitsyn on 12/23/19
 */

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.deft.auth.model.UserEntity;
import ru.deft.auth.repository.UserRepository;
import ru.deft.auth.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long createUser(UserEntity user) {
        UserEntity save = userRepository.save(user);
        return save.getId();
    }
}
