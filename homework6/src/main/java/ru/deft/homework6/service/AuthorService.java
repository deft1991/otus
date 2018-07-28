package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.repository.AuthorDaoJdbc;

import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorDaoJdbc authorDaoJdbc;

  public List<Author> findAll() {
    return authorDaoJdbc.findAll();
  }

  public void printAuthors(){
    authorDaoJdbc.findAll().forEach(System.out::println);
  }

  public void printAuthorById(String id) {
    System.out.println(authorDaoJdbc.getById(id));
  }

  public void addAuthor(String name) {
    authorDaoJdbc.insert(name);
  }
}
