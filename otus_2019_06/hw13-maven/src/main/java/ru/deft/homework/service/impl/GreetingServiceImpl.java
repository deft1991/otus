package ru.deft.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.deft.homework.api.model.User;
import ru.deft.homework.service.GreetingService;

import java.util.HashMap;
import java.util.Map;

/*
 * Created by sgolitsyn on 10/14/19
 */

@Service
@RequiredArgsConstructor
public class GreetingServiceImpl implements GreetingService {

    private final UserServiceImpl userService;

    @Override
    public Map<String, String> sayHello(String name) {
        Map<String, String> map = new HashMap<>();
        map.put(name, "Hello, " + name);
        userService.save(new User("deft", "qqq"));
        userService.save(new User("admin", "admin"));
        userService.save(new User("user", "user"));
        return map;
    }
}
