package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Getter
@Setter
public class BookToAuthor {
  private int id;
  private int bookId;
  private int authorId;
}
