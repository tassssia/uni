package com.example.lab1Back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDiscount {
    private int id;
    private int userId;
    private int tourCompanyId;
    private int discount;
}