package ru.deft.homework6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deft.homework6.domain.Genre;
import ru.deft.homework6.repository.GenreRepository;

import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepository genreRepository;

  public void printGenres() {
	genreRepository.findAll().forEach(System.out::println);
  }

  public void printGenreById(String id) {
	System.out.println(genreRepository.findById(UUID.fromString(id)).orElseThrow());
  }

  public void addGenre(String name) {
	System.out.println(genreRepository.save(new Genre(name)));
  }
}
