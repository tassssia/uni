package com.univ.lab2Back.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TourType {
    RESORT("Resort"),
    EXCURSION("Excursion"),
    SHOPPING("Shopping");

    private final String name;
}
