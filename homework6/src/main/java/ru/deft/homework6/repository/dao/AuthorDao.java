package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Author;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface AuthorDao {

  int count();

  Author getById(int id);

  List<Author> findAll();

  void insert(Author author);
}
