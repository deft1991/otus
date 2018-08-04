package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Commentary;
import ru.deft.homework6.repository.BookRepository;
import ru.deft.homework6.repository.CommentaryRepository;

import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
  private final CommentaryRepository commentaryRepository;
  private final BookRepository bookRepository;

  public void addComment(String bookId, String comment) {
	Commentary commentary = new Commentary(comment);
	commentary.setBook(bookRepository.findById(UUID.fromString(bookId)).orElseThrow());
	commentaryRepository.save(commentary);
  }

  public void readComment(String bookId) {
	commentaryRepository.findAllByBookId(UUID.fromString(bookId)).forEach(System.out::println);
  }
}
