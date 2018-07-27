package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/18/2018
 */
@Entity
@Getter
@Setter
public class BookToAuthor extends Identifiable {
  @ManyToOne
  @JoinColumn(name = "BOOK_ID", nullable = false)
  private Book book;
  @ManyToOne
  @JoinColumn(name = "AUTHOR_ID", nullable = false)
  private Author author;
}
