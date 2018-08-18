package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Author;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface AuthorRepository {

  int count();

  Author getById(String id);

  List<Author> findAll();

  Author insert(String name);
}
