package com.univ.lab2Back.dao;

import com.univ.lab2Back.entity.TourCompany;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourCompanyDAO extends CrudRepository<TourCompany, Integer> {
}
