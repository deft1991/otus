package ru.deft.homework.api.service;

import java.util.Optional;

public interface DBCommonService<T> {

    long save(T user);

    void update(T user);

    void createOrUpdate(T user); // опционально.

    Optional<T> findById(long id);
}
