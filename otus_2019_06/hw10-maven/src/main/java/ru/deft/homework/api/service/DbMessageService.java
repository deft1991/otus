package ru.deft.homework.api.service;

import ru.deft.homework.api.model.Message;

import java.util.UUID;

/*
 * Created by sgolitsyn on 11/25/19
 */
public interface DbMessageService {

    Message getById(Long id);

    UUID save(Message message);
}
