package com.crewmaker.controller;

import com.crewmaker.dto.response.UserProfileDetails;
import com.crewmaker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userProfile")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public UserProfileDetails getUserProfiles(@PathVariable String username) {
        return userRepository.findByUsernameUserProfile(username);
    }

}