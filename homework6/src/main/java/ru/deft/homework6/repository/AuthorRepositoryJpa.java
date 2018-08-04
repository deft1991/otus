package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Author;
import ru.deft.homework6.repository.dao.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
@Transactional
@RequiredArgsConstructor
@SuppressWarnings({"SqlResolve", "JpaQlInspection"})
public class AuthorRepositoryJpa implements AuthorRepository {

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public int count() {
	return entityManager
			.createQuery("select count(a) from author", Integer.class)
			.getSingleResult();
  }

  @Override
  public Author getById(String id) {
	return entityManager.find(Author.class, UUID.fromString(id));
  }

  @Override
  public List<Author> findAll() {
	return entityManager
			.createQuery("select a from Author a", Author.class)
			.getResultList();
  }

  @Override
  @Transactional
  public Author insert(String name) {
	Author a = new Author(name);
	entityManager.merge(a);
	return a;
  }
}
