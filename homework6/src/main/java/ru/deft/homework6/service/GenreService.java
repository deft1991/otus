package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.repository.GenreRepositoryJpa;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepositoryJpa genreDaoJdbc;

  public void printGenres() {
	genreDaoJdbc.findAll().forEach(System.out::println);
  }

  public void printGenreById(String id) {
	System.out.println(genreDaoJdbc.getById(id));
  }

  public void addGenre(String name) {
	System.out.println(genreDaoJdbc.insert(name));
  }
}
