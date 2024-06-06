package com.univ.lab2Back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDiscount {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private int discount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "tour_company_id")
    private TourCompany tourCompany;
}