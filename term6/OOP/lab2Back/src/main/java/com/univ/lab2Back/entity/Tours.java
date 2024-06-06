package com.univ.lab2Back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tours {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String description;
    private Double price;

    @Enumerated(value = EnumType.STRING)
    private TourType type;

    private Date date;
    private boolean isHot;
    private String city;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tour_company_id")
    private TourCompany tourCompany;

    @ManyToMany(mappedBy = "tours")
    private List<Users> users;
}
