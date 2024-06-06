package com.univ.lab2Back.service;

import com.univ.lab2Back.dao.ToursDAO;
import com.univ.lab2Back.dao.UsersDAO;
import com.univ.lab2Back.dto.UserTourDto;
import com.univ.lab2Back.entity.Tours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerCartService {
    private final UsersDAO usersDAO;
    private final ToursDAO toursDAO;

    @Autowired
    public CustomerCartService(UsersDAO usersDAO, ToursDAO toursDAO) {
        this.usersDAO = usersDAO;
        this.toursDAO = toursDAO;
    }

    public void customBuyTour(Integer userId, Integer tourId, Integer tourCompanyId) {
        var user = usersDAO.findById(userId).orElseThrow();
        user.getTours().removeIf(tour -> Objects.equals(tour.getId(), tourId));
    }

    public void customerAddTourToCart(UserTourDto userTourDto) {
        var tour = toursDAO.findById(userTourDto.getTourId())
                .orElseThrow();
        var user = usersDAO.findById(userTourDto.getUserId())
                .orElseThrow();
        user.getTours().add(tour);
    }

    public List<Tours> getAllToursFromCart(Integer userId) {
        var user = usersDAO.findById(userId)
                .orElseThrow();
        return user.getTours();
    }

    public void deleteTourFromCart(Integer userId, Integer tourId) {
        var user = usersDAO.findById(userId).orElseThrow();
        user.getTours().removeIf(tour -> Objects.equals(tour.getId(), tourId));
    }
}
