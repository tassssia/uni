package com.example.lab1Back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersToursCart {
    private int id;
    private int userId;
    private int tourId;
}