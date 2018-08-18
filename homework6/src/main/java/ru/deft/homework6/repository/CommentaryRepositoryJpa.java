package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Book;
import ru.deft.homework6.domain.Commentary;
import ru.deft.homework6.repository.dao.CommentaryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sergey Golitsyn (deft) on 28.07.2018
 */
@SuppressWarnings({"SqlResolve", "JpaQlInspection"})
@Repository
@RequiredArgsConstructor
public class CommentaryRepositoryJpa implements CommentaryRepository {
  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public Commentary getById(UUID id) {
	return null;
  }

  @Override
  public List<Commentary> findAll() {
	return null;
  }

  @Override
  public void insert(String commentary, String bookId) {
	Commentary comment = new Commentary();
	comment.setMessage(commentary);
	comment.setBook(entityManager.find(Book.class, UUID.fromString(bookId)));
	entityManager.merge(comment);
  }

  @Override
  public List<Commentary> findAllComments(String bookId) {
	TypedQuery<Commentary> query = entityManager.createQuery("select c from Commentary c where c.book.id=:bookId", Commentary.class);
	query.setParameter("bookId", UUID.fromString(bookId));
	return query.getResultList();
  }
}
