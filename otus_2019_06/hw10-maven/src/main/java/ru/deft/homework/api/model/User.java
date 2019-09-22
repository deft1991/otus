package ru.deft.homework.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity @Table(name = "users") @Getter @Setter @NoArgsConstructor public class User {

    @Id @GeneratedValue private Long id;
    @Column(name = "name", nullable = false) private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phone> phones;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", address=" + address + ", phones=" + phones + '}';
    }
}
