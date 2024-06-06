package com.univ.lab2Back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDiscountDto {
    private Integer id;
    private Integer userId;
    private Integer tourCompanyId;
    private Integer discount;
}
