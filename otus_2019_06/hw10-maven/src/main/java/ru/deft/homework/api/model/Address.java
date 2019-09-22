package ru.deft.homework.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor @Entity @Table(name = "address") public class Address {

    @Id @GeneratedValue private Long id;

    @Column(name = "street", nullable = false) private String street;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY) private User user;

    @Override public String toString() {
        return "Address{" + "id=" + id + ", street='" + street + '\'' + '}';
    }
}
