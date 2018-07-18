package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Book;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface GenreDao {

  int count();

  Book getById(int id);

  List<Book> findAll();

  void insert(Book book);
}
