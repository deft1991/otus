package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Genre extends Identifiable {

  private String name;

  @ManyToMany(mappedBy = "genres")
  private List<Book> books;

  public Genre(String name) {
	this.name = name;
  }

  @Override
  public String toString() {
	return "Genre{" +
			"id='" + super.getId() + '\'' +
			"name='" + name + '\'' +
			'}';
  }
}
