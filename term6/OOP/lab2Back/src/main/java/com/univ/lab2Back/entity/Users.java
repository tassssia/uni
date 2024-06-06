package com.univ.lab2Back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private Double money;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UsersDiscount> usersDiscounts;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "user_tour",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_id"))
    private List<Tours> tours;
}