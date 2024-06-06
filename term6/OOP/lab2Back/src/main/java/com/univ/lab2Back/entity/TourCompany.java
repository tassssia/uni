package com.univ.lab2Back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "tourCompany", cascade = CascadeType.ALL)
    private List<Tours> tours;

    @OneToMany(mappedBy = "tourCompany", cascade = CascadeType.ALL)
    private List<UsersDiscount> usersDiscounts;
}
