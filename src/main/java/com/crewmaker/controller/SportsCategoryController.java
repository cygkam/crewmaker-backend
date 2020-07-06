package com.crewmaker.controller;

import com.crewmaker.dto.response.SportsCategoryDetails;
import com.crewmaker.repository.SportsCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SportsCategoryController {
    @Autowired
    SportsCategoryRepository sportsCategoryRepository;

    @GetMapping("/sportscategories")
    List<SportsCategoryDetails> getAllSportsCategory() {
        return sportsCategoryRepository.findAll().stream().map(sportsCategory -> new SportsCategoryDetails(sportsCategory)).collect(Collectors.toList());
    }

    @GetMapping("/sportscategoriesplaces")
    List<SportsCategoryDetails> getAllSportsCategoryByEventID(@RequestParam(name = "eventPlaceID") Long eventPlaceID) {
        return sportsCategoryRepository.findByEventPlaceId(eventPlaceID);
    }
}
