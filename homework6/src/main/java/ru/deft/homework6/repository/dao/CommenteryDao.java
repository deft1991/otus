package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Commentary;

import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
public interface CommenteryDao {

  int count();

  Commentary getById(UUID id);

  List<Commentary> findAll();

  void insert(Commentary commentary);
}
