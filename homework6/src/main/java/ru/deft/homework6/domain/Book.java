package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book extends Identifiable {

  private String name;
  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
  private List<BookToAuthor> bookToAuthors;
  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
  private List<BookToGenre> bookToGenres;
  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
  private List<Commentary> commentaries;

  public Book(UUID id, String name) {
	super(id);
	this.name = name;
  }

  @Override
  public String toString() {
	List<String> authorNames = getBookToAuthors()
			.stream()
			.map(BookToAuthor::getAuthor)
			.map(Author::getName)
			.collect(Collectors.toList());
	List<String> genreNames = getBookToGenres()
			.stream()
			.map(BookToGenre::getGenre)
			.map(Genre::getName)
			.collect(Collectors.toList());

	StringBuilder sb = new StringBuilder();
	sb.append("Book { ");
	sb.append("id= '" + super.getId() + '\'');
	sb.append(", name = '" + name + '\'');
	if (authorNames.size() > 1)
	  sb.append(", authors = '").append(StringUtils.join(authorNames, ",")).append('\'');
	else {
	  sb.append(", author = '").append(authorNames.get(0)).append('\'');
	}
	if (genreNames.size() > 1) {
	  sb.append(", genres = '").append(StringUtils.join(genreNames, ",")).append('\'');
	} else {
	  sb.append(", genre = '").append(genreNames.get(0)).append('\'');
	}
	return sb.toString();
  }
}
