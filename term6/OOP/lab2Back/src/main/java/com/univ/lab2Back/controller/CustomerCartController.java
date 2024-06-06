package com.univ.lab2Back.controller;

import com.univ.lab2Back.dto.UserTourDto;
import com.univ.lab2Back.service.CustomerCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CustomerCartController {
    private CustomerCartService customerCartService;

    @Autowired
    public CustomerCartController(CustomerCartService customerCartService) {
        this.customerCartService = customerCartService;
    }

    @PostMapping
    public ResponseEntity<?> customerAddTourToCart(@RequestBody UserTourDto userTourDto) {
        customerCartService.customerAddTourToCart(userTourDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllToursFromCart(@RequestParam Integer userId) {
        var tours = customerCartService.getAllToursFromCart(userId);
        return ResponseEntity.ok(tours);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTourFromCart(@RequestParam Integer userId,
                                                @RequestParam Integer tourId) {
        customerCartService.deleteTourFromCart(userId, tourId);
        return ResponseEntity.ok().build();
    }
}
