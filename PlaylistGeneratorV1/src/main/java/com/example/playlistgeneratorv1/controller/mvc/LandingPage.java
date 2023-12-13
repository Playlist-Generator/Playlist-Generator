package com.example.playlistgeneratorv1.controller.mvc;

import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.services.contracts.TrackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class LandingPage {

    private final TrackServices trackServices;

    @Autowired
    public LandingPage(TrackServices trackServices) {
        this.trackServices = trackServices;
    }

    @GetMapping
    public String showLandingPage(Model model) {
        List<Tracks> topTracks = trackServices.findTopTrackAcrossGenres(10);
        model.addAttribute("topTracks", topTracks);
        return "LandingPage";
    }
}
