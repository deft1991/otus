package ru.deft.homework6.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.dao.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * @genre Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Repository
@Transactional
@RequiredArgsConstructor
@SuppressWarnings({"SqlResolve", "JpaQlInspection"})
public class GenreRepositoryJpa implements GenreRepository {

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public int count() {
	return entityManager
			.createQuery("select count (g) from Genre g", Integer.class)
			.getSingleResult();
  }

  @Override
  public Genre getById(String id) {
	return entityManager.find(Genre.class, UUID.fromString(id));
  }

  @Override
  public List<Genre> findAll() {
	return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
  }

  @Override
  @Transactional
  public Genre insert(String name) {
	Genre genre = new Genre(name);
	entityManager.merge(genre);
	return genre;
  }
}