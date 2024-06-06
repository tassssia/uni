package com.univ.lab2Back.controller;

import com.univ.lab2Back.service.CustomerCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart-buy")
public class CustomerCartBuyController {
    private CustomerCartService customerCartService;

    @Autowired
    public CustomerCartBuyController(CustomerCartService customerCartService) {
        this.customerCartService = customerCartService;
    }

    @PostMapping
    public ResponseEntity<?> customBuyTour(@RequestParam Integer userId,
                                        @RequestParam Integer tourId,
                                        @RequestParam Integer tourCompanyId) {
        customerCartService.customBuyTour(userId, tourId, tourCompanyId);
        return ResponseEntity.ok().build();
    }
}
