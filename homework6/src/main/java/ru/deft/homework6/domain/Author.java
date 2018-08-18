package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author extends Identifiable {

  @NotNull
  private String name;

  @ManyToMany(mappedBy = "authors")
  private List<Book> books;

  public Author(String name) {
	this.name = name;
  }

  @Override
  public String toString() {
	return "Author{" +
			"id='" + super.getId() + '\'' +
			"name='" + name + '\'' +
			'}';
  }
}
