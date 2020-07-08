package com.crewmaker.controller;

import com.crewmaker.dto.response.ApiResponse;
import com.crewmaker.dto.response.ImageResponse;
import com.crewmaker.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping("/event-place-image/{eventPlaceID}")
    public ResponseEntity<ImageResponse> getEventPlaceImage(@PathVariable(value = "eventPlaceID") Long eventPlaceID) {
        return Optional.ofNullable(imageService.getEventPlaceImage(eventPlaceID, 256, 256)).map(retrivedImage -> ResponseEntity
                .ok().body(new ImageResponse(retrivedImage, "eventPlace" + eventPlaceID))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping("/event-place-image/{eventPlaceID}")
    public ResponseEntity<?> uploadEventPlaceImage(@RequestParam("file") MultipartFile file, @PathVariable(value = "eventPlaceID") Long eventPlaceID) throws IOException {
        boolean successfulUpload = imageService.uploadEventPlaceImage(file, eventPlaceID);
        return ResponseEntity.ok(new ApiResponse(successfulUpload, successfulUpload ? "Image uploaded successfully" : "Error, couldn't upload image"));
    }

    @GetMapping("/user-profile-image/{username}")
    public ResponseEntity<ImageResponse> getUserProfileImage(@PathVariable(value = "username") String username, @RequestParam(value = "isSmall", required = true) boolean isSmall) {
        return Optional.ofNullable(imageService.getUserProfileImage(username, isSmall)).map(retrivedImage -> ResponseEntity
                .ok().body(new ImageResponse(retrivedImage, "userProfileImage" + username))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping("/user-profile-image")
    public ResponseEntity<?> uploadUserImage(@RequestParam("file") MultipartFile file) throws IOException {
        boolean successfulUpload = imageService.uploadUserImage(file);
        return ResponseEntity.ok(new ApiResponse(successfulUpload, successfulUpload ? "Image uploaded successfully" : "Error, couldn't upload image"));
    }
}