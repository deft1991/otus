package ru.deft.telegrambot.service;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.deft.telegrambot.model.Anonymous;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/*
 * Created by sgolitsyn on 11/22/19
 */
@Component
public final class AnonymousService {

    private final Set<Anonymous> mAnonymouses;

    public AnonymousService() {
        mAnonymouses = new HashSet<>();
    }

    public boolean setUserDisplayedName(User user, String name) {

        if (!isDisplayedNameTaken(name)) {
            mAnonymouses.stream().filter(a -> a.getMUser().equals(user)).forEach(a -> a.setMDisplayedName(name));
            return true;
        }

        return false;
    }

    public boolean removeAnonymous(User user) {
        return mAnonymouses.removeIf(a -> a.getMUser().equals(user));
    }

    public boolean addAnonymous(Anonymous anonymous) {
        return mAnonymouses.add(anonymous);
    }

    public boolean hasAnonymous(User user) {
        return mAnonymouses.stream().anyMatch(a -> a.getMUser().equals(user));
    }

    public String getDisplayedName(User user) {

        Anonymous anonymous = mAnonymouses.stream().filter(a -> a.getMUser().equals(user)).findFirst().orElse(null);

        if (anonymous == null) {
            return null;
        }
        return anonymous.getMDisplayedName();
    }

    public Stream<Anonymous> anonymouses() {
        return mAnonymouses.stream();
    }

    private boolean isDisplayedNameTaken(String name) {
        return mAnonymouses.stream().anyMatch(a -> Objects.equals(a.getMDisplayedName(), name));
    }
}
