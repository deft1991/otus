package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author extends Identifiable {

  private String name;

  public Author(UUID id, String name) {
	super(id);
	this.name = name;
  }
}
