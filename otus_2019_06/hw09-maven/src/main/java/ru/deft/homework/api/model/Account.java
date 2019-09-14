package ru.deft.homework.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.deft.homework.annotations.Id;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor public class Account {

    @Id private Long id;
    private String type;
    private int rest;

    public Account(String type, int rest) {
        this.type = type;
        this.rest = rest;
    }
}
