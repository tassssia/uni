package com.univ.lab2Back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Date date;
    private Boolean isHot;
    private String city;
    private Integer tourCompanyId;
}
