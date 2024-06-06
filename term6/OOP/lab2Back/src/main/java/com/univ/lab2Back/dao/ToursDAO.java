package com.univ.lab2Back.dao;

import com.univ.lab2Back.entity.Tours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToursDAO extends CrudRepository<Tours, Integer> {
}
