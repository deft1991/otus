package ru.deft.homework.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.deft.homework.annotations.Id;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor public class User {

    @Id private Long id;
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
