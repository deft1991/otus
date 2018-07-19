package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Genre;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface GenreDao {

  int count();

  Genre getById(int id);

  List<Genre> findAll();

  void insert(Genre genre);
}
