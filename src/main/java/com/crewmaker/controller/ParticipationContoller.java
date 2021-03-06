package com.crewmaker.controller;

import com.crewmaker.entity.Event;
import com.crewmaker.entity.Participation;
import com.crewmaker.entity.User;
import com.crewmaker.exception.ResourceNotFoundException;
import com.crewmaker.repository.EventRepository;
import com.crewmaker.repository.ParticipationRepository;
import com.crewmaker.repository.UserRepository;
import com.crewmaker.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ParticipationContoller {

    @Autowired
    EventRepository eventRepository;
    @Autowired

    UserRepository userRepository;

    @Autowired
    ParticipationRepository participationRepository;

    @GetMapping("/api/joinevent")
    public ResponseEntity<?> joinEvent(@RequestParam Long eventID){
        Event event = eventRepository.findByEventId(eventID);
        long actualParticipants = eventRepository.countAllByEventParticipationsIdEvent(event);

        if(actualParticipants >= event.getMaxPlayers()) throw new ResourceNotFoundException("Event","EventParticipants",event);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Participation newParticipation = new Participation(user,event,0);
        participationRepository.save(newParticipation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(newParticipation.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/api/leaveevent")
    public ResponseEntity<?> leaveEvent(@RequestParam Long eventID){
        Event event = eventRepository.findByEventId(eventID);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Participation participation = participationRepository.findByIdEventAndIdUser(event, user);
        participationRepository.delete(participation);

        return new ResponseEntity<>(eventID, HttpStatus.OK);
    }

    @GetMapping("/api/existsparticipation")
    public boolean existParticipation(@RequestParam Long eventID){
        Event event = eventRepository.findByEventId(eventID);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return  participationRepository.existsByIdEventAndIdUser(event,user);
    }

    @GetMapping("/api/eventParticipants")
    public List<User> getEventParticipants(@RequestParam Long eventID){
        Event event = eventRepository.findByEventId(eventID);
        return userRepository.findDistinctByUserParticipationsId_IdEvent(event);
    }
}
