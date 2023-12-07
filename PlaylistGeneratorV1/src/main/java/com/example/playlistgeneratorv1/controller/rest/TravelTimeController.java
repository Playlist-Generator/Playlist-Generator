
package com.example.playlistgeneratorv1.controller.rest;

import com.example.playlistgeneratorv1.services.TravelTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

@RestController
public class TravelTimeController {


    private final TravelTimeService travelTimeService;


    @Autowired
    public TravelTimeController(TravelTimeService travelTimeService) {
        this.travelTimeService = travelTimeService;
    }

    @GetMapping("/travel-time")
    public Time getTravelTime(@RequestParam String origin, @RequestParam String destination) {
        return travelTimeService.getTravelTime(origin, destination);
    }
}
