package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.repository.AuthorRepositoryJpa;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepositoryJpa authorDaoJdbc;

  public List<Author> findAll() {
	return authorDaoJdbc.findAll();
  }

  public void printAuthors() {
	authorDaoJdbc.findAll().forEach(System.out::println);
  }

  public void printAuthorById(String id) {
	System.out.println(authorDaoJdbc.getById(id));
  }

  public void addAuthor(String name) {
	System.out.println(authorDaoJdbc.insert(name));
  }

  public void count() {
	System.out.println(authorDaoJdbc.count());
  }
}
