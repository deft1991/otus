package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.BookToAuthor;
import ru.deft.homework6.domain.BookToGenre;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
@Transactional
@RequiredArgsConstructor
@SuppressWarnings("ALL")
public class BookRepositoryJpa implements BookRepository {

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public Long count() {
	return entityManager
			.createQuery("select count(b) from Book b", Long.class)
			.getSingleResult();
  }

  /**
   * Тут использую разные сбособы. Просто самому интересно как работает
   */
  @Override
  public Book getById(String id) {
	final HashMap<String, String> params = new HashMap<>();
	params.put("id", id);
	Book book = entityManager.find(Book.class, UUID.fromString(id));
	return book;
  }

  @Override
  public List<Book> findAll() {
	List<Book> books = entityManager
			.createQuery("select b from Book b", Book.class)
			.getResultList();
	return books;
  }

  public void addBook(String bookName, String authorId, String genreId) {
	try {
	  if (StringUtils.isEmpty(bookName)) {
		throw new RuntimeException("Book name can`t be empty");
	  }
	  Book book = new Book(bookName);
	  entityManager.persist(book);
	  if (!StringUtils.isEmpty(authorId)) {
		BookToAuthor ba = new BookToAuthor();
		ba.setBook(book);
		ba.setAuthor(entityManager.find(Author.class, UUID.fromString(authorId)));
		entityManager.merge(ba);
	  }
	  if (!StringUtils.isEmpty(genreId)) {
		BookToGenre bg = new BookToGenre();
		bg.setBook(book);
		bg.setGenre(entityManager.find(Genre.class, UUID.fromString(genreId)));
		entityManager.merge(bg);
	  }
	} catch (Exception e) {
	  e.printStackTrace();
	}
  }
}
