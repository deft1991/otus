package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.BookToAuthor;
import ru.deft.homework6.domain.BookToGenre;
import ru.deft.homework6.repository.AuthorRepository;
import ru.deft.homework6.repository.BookRepository;
import ru.deft.homework6.repository.BookToAuthorRepositoty;
import ru.deft.homework6.repository.BookToGenreRepository;
import ru.deft.homework6.repository.GenreRepository;

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

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;
  private final BookToAuthorRepositoty bookToAuthorRepositoty;
  private final BookToGenreRepository bookToGenreRepository;


  public List<Book> findAll() {
	return bookRepository.findAll();
  }

  public void printBooks() {
	bookRepository.findAll().forEach(System.out::println);
  }

  public Book getById(String id) {
	return bookRepository.findById(UUID.fromString(id)).orElseThrow();
  }

  public void addBook(String bookName) {
	this.addBook(bookName, null, null);
  }

  public void addBook(String bookName, String authorId) {
	this.addBook(bookName, authorId, null);
  }

  public void addBook(String bookName, String authorId, String genreId) {
	try {
	  if (StringUtils.isEmpty(bookName)) {
		throw new RuntimeException("Book name can`t be empty");
	  }
	  Book book = new Book(bookName);
	  bookRepository.save(book);
	  if (!StringUtils.isEmpty(authorId)) {
		BookToAuthor ba = new BookToAuthor();
		ba.setBook(book);
		ba.setAuthor(authorRepository.findById(UUID.fromString(authorId)).orElseThrow());
		bookToAuthorRepositoty.save(ba);
	  }
	  if (!StringUtils.isEmpty(genreId)) {
		BookToGenre bg = new BookToGenre();
		bg.setBook(book);
		bg.setGenre(genreRepository.findById(UUID.fromString(genreId)).orElseThrow());
		bookToGenreRepository.save(bg);
	  }
	} catch (Exception e) {
	  e.printStackTrace();
	}
  }

  public void booksCount() {
	System.out.println(bookRepository.count());
  }

  public void printOne(String id) {
	System.out.println(bookRepository.findById(UUID.fromString(id)).orElseThrow());
  }
}
