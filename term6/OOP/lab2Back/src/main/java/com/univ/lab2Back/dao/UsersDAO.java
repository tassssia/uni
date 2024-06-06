package com.univ.lab2Back.dao;

import com.univ.lab2Back.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends CrudRepository<Users, Integer> {
}
