package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

  private int id;
  private String name;
  private List<Author> authors = new ArrayList<>();
  private List<Genre> genres = new ArrayList<>();

  public Book(int id, String name) {
	this.id = id;
	this.name = name;
  }

  public Book(int id, String name, List<Author> authors, List<Genre> genres) {
	this.id = id;
	this.name = name;
	this.authors = authors;
	this.genres = genres;
  }

  public void addAuthor(Author author){
    authors.add(author);
  }
  public void addGenre(Genre genre){
    genres.add(genre);
  }
}
