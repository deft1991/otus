package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Genre;

import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface GenreDao {

  int count();

  Genre getById(String id);

  List<Genre> findAll();

  void insert(String name);
}
