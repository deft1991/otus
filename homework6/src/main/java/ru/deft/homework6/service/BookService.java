package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.repository.BookRepositoryJpa;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

  private final BookRepositoryJpa bookDaoJdbc;

  public List<Book> findAll() {
	return bookDaoJdbc.findAll();
  }

  public void printBooks() {
	bookDaoJdbc.findAll().forEach(System.out::println);
  }

  public Book getById(String id) {
	return bookDaoJdbc.getById(id);
  }

  public void addBook(String bookName) {
	this.addBook(bookName, null, null);
  }

  public void addBook(String bookName, String authorId) {
	this.addBook(bookName, authorId, null);
  }

  public void addBook(String bookName, String authorId, String genreId) {
	bookDaoJdbc.addBook(bookName, authorId, genreId);
  }

  public void booksCount() {
	System.out.println(bookDaoJdbc.count());
  }

  public void printOne(String id) {
	System.out.println(bookDaoJdbc.getById(id));
  }
}
