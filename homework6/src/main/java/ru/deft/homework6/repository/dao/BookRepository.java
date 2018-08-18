package ru.deft.homework6.repository.dao;

import ru.deft.homework6.domain.Book;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
public interface BookRepository {

  Long count();

  Book getById(String id);

  List<Book> findAll();

  void addBook(String bookName, String authorName, String genreName);
}
