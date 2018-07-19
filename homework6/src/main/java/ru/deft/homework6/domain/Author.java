package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {

  private int id;
  private String name;

  public Author(int id, String name) {
	this.id = id;
	this.name = name;
  }
}
