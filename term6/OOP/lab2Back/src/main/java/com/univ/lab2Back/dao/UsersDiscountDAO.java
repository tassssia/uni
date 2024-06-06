package com.univ.lab2Back.dao;

import com.univ.lab2Back.entity.UsersDiscount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDiscountDAO extends CrudRepository<UsersDiscount, Integer> {
}
