package ru.deft.homework.api.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity @Table(name = "phone") @Data public class Phone {

    @Id @GeneratedValue private Long id;

    @Column(name = "number") private String number;

    @ManyToOne @JoinColumn(name = "user_id", referencedColumnName = "id") private User user;
}
