package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Author;

import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface AuthorDao {

  int count();

  Author getById(String id);

  List<Author> findAll();

  void insert(String name);
}
