package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.repository.BookDaoJdbc;

import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

  private final BookDaoJdbc bookDaoJdbc;

  public List<Book> findAll() {
	return bookDaoJdbc.findAll();
  }

  public void printBooks(){
	bookDaoJdbc.findAll().forEach(System.out::println);
  }

  public Book getById(UUID id) {
	return bookDaoJdbc.getById(id);
  }

  public void addBook(String bookName, String authorId, String genreId) {
	bookDaoJdbc.addBook(bookName, authorId, genreId);
  }

  public void booksCount() {
	System.out.println(bookDaoJdbc.count());
  }
}
