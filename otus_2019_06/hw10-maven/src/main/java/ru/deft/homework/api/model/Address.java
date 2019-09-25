package ru.deft.homework.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "street", nullable = false)
  private String street;

  @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
  private User user;

  @Override
  public String toString() {
    return "Address{" + "id=" + id + ", street='" + street + '\'' + '}';
  }

    public Address(String street) {
        this.street = street;
    }
}
