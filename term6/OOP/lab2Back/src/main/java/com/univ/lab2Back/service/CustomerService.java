package com.univ.lab2Back.service;

import com.univ.lab2Back.dao.ToursDAO;
import com.univ.lab2Back.entity.Tours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {
    private final ToursDAO toursDAO;

    @Autowired
    public CustomerService(ToursDAO toursDAO) {
        this.toursDAO = toursDAO;
    }

    public List<Tours> getAllTours() {
        return StreamSupport.stream(toursDAO.findAll().spliterator(), false)
                .toList();
    }
}
