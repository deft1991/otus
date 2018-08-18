package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.repository.CommentaryRepositoryJpa;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
  private final CommentaryRepositoryJpa commentaryDaoJdbc;

  public void addComment(String book, String comment) {
	commentaryDaoJdbc.insert(comment, book);
  }

  public void readComment(String book) {
	commentaryDaoJdbc.findAllComments(book).forEach(System.out::println);
  }
}
