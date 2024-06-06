package com.univ.lab2Back.controller;

import com.univ.lab2Back.dto.UserDiscountDto;
import com.univ.lab2Back.service.TourAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour-agent-customers")
public class TourAgentCustomerController {
    private final TourAgentService tourAgentService;

    @Autowired
    public TourAgentCustomerController(TourAgentService tourAgentService) {
        this.tourAgentService = tourAgentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsersWhoBoughtAtLeastOneTour(@RequestParam Integer tourCompanyId) {
        var usersList = tourAgentService.getAllUsersWhoBoughtAtLeastOneTour(tourCompanyId);
        return ResponseEntity.ok(usersList);
    }

    @PutMapping
    public ResponseEntity<?> updateUsersDiscount(@RequestBody List<UserDiscountDto> discountDtoList) {
        tourAgentService.updateUsersDiscount(discountDtoList);
        return ResponseEntity.ok().build();
    }
}
