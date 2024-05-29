package com.example.lab1Back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tours {
    private int id;
    private String name;
    private String description;
    private Double price;
    private TourType type;
    private Date date;
    private boolean isHot;
    private String city;
    private int tourCompanyId;

    public Double getPrice() {
        return isHot ? price * 0.5 : price;
    }
}
