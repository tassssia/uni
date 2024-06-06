package com.univ.lab2Back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univ.lab2Back.dao.TourCompanyDAO;
import com.univ.lab2Back.dao.ToursDAO;
import com.univ.lab2Back.dao.UsersDiscountDAO;
import com.univ.lab2Back.dto.TourDto;
import com.univ.lab2Back.dto.UserDiscountDto;
import com.univ.lab2Back.entity.Tours;
import com.univ.lab2Back.entity.Users;
import com.univ.lab2Back.entity.UsersDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
public class TourAgentService {
    private final ToursDAO toursDAO;
    private final UsersDiscountDAO usersDiscountDAO;
    private final TourCompanyDAO tourCompanyDAO;

    @Autowired
    public TourAgentService(ToursDAO toursDAO, UsersDiscountDAO usersDiscountDAO,
                            TourCompanyDAO tourCompanyDAO) {
        this.toursDAO = toursDAO;
        this.usersDiscountDAO = usersDiscountDAO;
        this.tourCompanyDAO = tourCompanyDAO;
    }

    @Transactional
    public void createTour(TourDto tourDto) {
        ObjectMapper mapper = new ObjectMapper();
        Tours tours = mapper.convertValue(tourDto, Tours.class);
        toursDAO.save(tours);
    }

    @Transactional
    public void updateTour(TourDto tourDto) {
        ObjectMapper mapper = new ObjectMapper();
        Tours tours = mapper.convertValue(tourDto, Tours.class);
        Tours oldTour = toursDAO.findById(tourDto.getId())
                .orElseThrow(() -> new IllegalStateException("Tour with id " + tourDto.getId() + " does not exist"));
        tours.setId(oldTour.getId());
        toursDAO.save(tours);
    }

    public List<Users> getAllUsersWhoBoughtAtLeastOneTour(Integer tourCompanyId) {
        return StreamSupport.stream(usersDiscountDAO.findAll().spliterator(), false)
                .filter(userDiscount -> Objects.equals(userDiscount.getTourCompany().getId(), tourCompanyId))
                .map(UsersDiscount::getUser)
                .toList();
    }

    public void updateUsersDiscount(List<UserDiscountDto> discountDtoList) {
        var updated = new ArrayList<UsersDiscount>();
        for (var discountDto : discountDtoList) {
            var usersDiscount = usersDiscountDAO.findById(discountDto.getId())
                    .orElseThrow(() -> new IllegalStateException("User discount with id " + discountDto.getId() + " does not exist"));
            usersDiscount.setDiscount(discountDto.getDiscount());
            updated.add(usersDiscount);
        }
        usersDiscountDAO.saveAll(updated);
    }

    public List<Tours> getAllToursForTourAgent(Integer tourAgentId) {
        return StreamSupport.stream(tourCompanyDAO.findAll().spliterator(), false)
                .filter(tourCompany -> Objects.equals(tourCompany.getId(), tourAgentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Tour company with id " + tourAgentId + " does not exist"))
                .getTours();
    }
}
