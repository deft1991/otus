package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.repository.AuthorRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public List<Author> findAll() {
	return authorRepository.findAll();
  }

  public void printAuthors() {
	authorRepository.findAll().forEach(System.out::println);
  }

  public void printAuthorById(String id) {
	System.out.println(authorRepository.findById(UUID.fromString(id)));
  }

  public void addAuthor(String name) {
	System.out.println(authorRepository.save(new Author(name)));
  }

  public void count() {
	System.out.println(authorRepository.count());
  }
}
