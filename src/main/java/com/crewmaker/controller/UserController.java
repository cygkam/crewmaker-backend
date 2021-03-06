package com.crewmaker.controller;

import com.crewmaker.config.security.AvailabilityConfirmation;
import com.crewmaker.config.security.user.CurrentUser;
import com.crewmaker.config.security.user.UserPrincipal;
import com.crewmaker.config.security.user.UserSummary;
import com.crewmaker.dto.response.UserProfileDetails;
import com.crewmaker.exception.ResourceNotFoundException;
import com.crewmaker.entity.User;
import com.crewmaker.repository.UserRepository;
import com.crewmaker.dto.response.ApiResponse;
import com.crewmaker.dto.request.UpdatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getAuthorities());
    }

    @GetMapping("/users/{username}")
    public UserProfileDetails getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return new UserProfileDetails(user.getUsername(), user.getEmail(), user.getPhoneNumber(), user.getDescription(), user.getName(), user.getSurname());
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UpdatedUser updatedUser) {
        User user = userRepository.findByUsername(updatedUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", updatedUser.getUsername()));

        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setDescription(updatedUser.getDescription());
        userRepository.save(user);

        return ResponseEntity.ok().body(new ApiResponse(true, "User data changed successfully"));
    }

    @GetMapping("/user/checkUsernameAvailability")
    public AvailabilityConfirmation checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return new AvailabilityConfirmation(!userRepository.existsByUsername(username));
    }

    @GetMapping("/user/checkEmailAvailability")
    public AvailabilityConfirmation checkEmailAvailability(@RequestParam(value = "email") String email) {
        return new AvailabilityConfirmation(!userRepository.existsByEmail(email));
    }

}
