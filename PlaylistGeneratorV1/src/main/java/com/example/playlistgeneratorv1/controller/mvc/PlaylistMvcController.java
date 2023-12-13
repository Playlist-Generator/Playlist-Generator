package com.example.playlistgeneratorv1.controller.mvc;

import com.example.playlistgeneratorv1.services.TravelTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  // Import the Controller annotation
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;

@Controller
public class PlaylistMvcController {

    private final TravelTimeService travelTimeService;

    @Autowired
    public PlaylistMvcController(TravelTimeService travelTimeService) {
        this.travelTimeService = travelTimeService;
    }

    @GetMapping("/travel-time1")
    public String showTravelTimeForm() {
        return "PlayListGenerator";
    }

    @GetMapping("/calculate-travel-time")
    public String calculateTravelTime(@RequestParam String origin, @RequestParam String destination, Model model) {
        Time travelTime = travelTimeService.getTravelTime(origin, destination);
        model.addAttribute("travelTime", travelTime);
        return "PlayListGenerator";
    }
}
