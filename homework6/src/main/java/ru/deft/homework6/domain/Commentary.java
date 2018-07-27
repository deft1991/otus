package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Entity
@Getter
@Setter
public class Commentary extends Identifiable{

  @NotNull
  String message;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  Book book;
}
