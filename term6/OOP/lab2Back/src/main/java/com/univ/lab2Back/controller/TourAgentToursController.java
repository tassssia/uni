package com.univ.lab2Back.controller;

import com.univ.lab2Back.service.TourAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tour-agent-tours")
public class TourAgentToursController {
    private final TourAgentService tourAgentService;

    @Autowired
    public TourAgentToursController(TourAgentService tourAgentService) {
        this.tourAgentService = tourAgentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllToursForTourAgent(@RequestParam Integer tourAgentId) {
        var tours = tourAgentService.getAllToursForTourAgent(tourAgentId);
        return ResponseEntity.ok(tours);
    }
}
