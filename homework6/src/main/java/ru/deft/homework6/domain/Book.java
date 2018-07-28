package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
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
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "BOOK_TO_AUTHOR",
		  joinColumns = @JoinColumn(name = "BOOK_ID"),
		  inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
  private List<Author> authors;
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "BOOK_TO_GENRE",
		  joinColumns = @JoinColumn(name = "BOOK_ID"),
		  inverseJoinColumns = @JoinColumn(name = "GENRE_ID"))
  private List<Genre> genres;
  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
  private List<Commentary> commentaries;

  public Book(UUID id, String name) {
	super(id);
	this.name = name;
  }

  @Override
  public String toString() {
	List<String> authorNames = new ArrayList<>();
	List<String> genreNames = new ArrayList<>();
	if (!CollectionUtils.isEmpty(getAuthors())) {
	  authorNames = getAuthors()
			  .stream()
			  .map(Author::getName)
			  .collect(Collectors.toList());
	}
	if (!CollectionUtils.isEmpty(getGenres())) {
	  genreNames = getGenres()
			  .stream()
			  .map(Genre::getName)
			  .collect(Collectors.toList());
	}
	StringBuilder sb = new StringBuilder();
	sb.append("Book { ");
	sb.append("id= '").append(super.getId()).append('\'');
	sb.append(", name = '").append(name).append('\'');
	if (authorNames.size() > 1)
	  sb.append(", authors = '").append(StringUtils.join(authorNames, ",")).append('\'');
	else if (authorNames.size() == 1) {
	  sb.append(", author = '").append(authorNames.get(0)).append('\'');
	}
	if (genreNames.size() > 1) {
	  sb.append(", genres = '").append(StringUtils.join(genreNames, ",")).append('\'');
	} else if (genreNames.size() == 1) {
	  sb.append(", genre = '").append(genreNames.get(0)).append('\'').append('}');
	}
	return sb.toString();
  }
}
