package com.crewmaker.controller;

import com.crewmaker.dto.response.UserOpinionDetails;
import com.crewmaker.entity.*;
import com.crewmaker.exception.ResourceNotFoundException;
import com.crewmaker.repository.UserOpinionRepository;
import com.crewmaker.repository.UserRepository;
import com.crewmaker.dto.response.ApiResponse;
import com.crewmaker.dto.request.NewUserOpinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserOpinionController {
    @Autowired
    private UserOpinionRepository userOpinionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/useropinions")
    public List<UserOpinionDetails> getUserOpinions(@RequestParam String username, @RequestParam String currentUser){
        return userOpinionRepository.findAllByUserAboutUsernameAndUserAuthorUsernameNot(username, currentUser).stream()
                .map(opinion -> new UserOpinionDetails(opinion)).collect(Collectors.toList());
    }

    @GetMapping("/useropinion")
    public UserOpinionDetails getUserOpinion(@RequestParam String username, @RequestParam String currentUser){
        UserOpinion opinion = userOpinionRepository.findByUserAboutUsernameAndUserAuthorUsername(username, currentUser);
        if(opinion != null) {
            return new UserOpinionDetails(opinion);
        } else {
            return null;
        }
    }

    @PostMapping("/newUserOpinion")
    public ResponseEntity<?> addNewUserOpinion(@RequestBody NewUserOpinion newUserOpinion) {
        System.out.println(newUserOpinion.toString());
        User author = userRepository.findByUsername(newUserOpinion.getOpinionAuthorName())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", newUserOpinion.getOpinionAuthorName()));

        User about = userRepository.findByUsername(newUserOpinion.getUserAbout())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", newUserOpinion.getOpinionAuthorName()));

        UserOpinion userOpinion = userOpinionRepository.findByUserAboutUsernameAndUserAuthorUsername(
                                    newUserOpinion.getUserAbout(), newUserOpinion.getOpinionAuthorName());

        if(userOpinion == null) {
            userOpinion = new UserOpinion(author, about, newUserOpinion.getTitle(),
                    newUserOpinion.getMessage(), newUserOpinion.getGrade());
        } else {
            userOpinion.setTitle(newUserOpinion.getTitle());
            userOpinion.setMessage(newUserOpinion.getMessage());
            userOpinion.setGrade(newUserOpinion.getGrade());
        }

        UserOpinion result = userOpinionRepository.save(userOpinion);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUserOpinionId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "New opinion about user was added"));
    }
}
