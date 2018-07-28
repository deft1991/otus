package ru.deft.homework6.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Golitsyn Sergey (sgolitsyn)
 * @since 7/27/2018
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Commentary extends Identifiable{

  @NotNull
  String message;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  Book book;

  public Commentary(@NotNull String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Commentary{ " +
            "message='" + message + '\'' +
            '}';
  }
}
