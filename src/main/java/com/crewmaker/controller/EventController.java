package com.crewmaker.controller;

import com.crewmaker.dto.response.EventProfileDetails;
import com.crewmaker.entity.*;
import com.crewmaker.exception.ResourceNotFoundException;
import com.crewmaker.repository.*;
import com.crewmaker.dto.response.ApiResponse;
import com.crewmaker.dto.request.UpdatedEvent;
import com.crewmaker.dto.request.NewEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CyclePeriodRepository cyclePeriodRepository;
    @Autowired
    private EventStatusRepository eventStatusRepository;
    @Autowired
    private EventPlaceRepository eventPlaceRepository;
    @Autowired
    private SportsCategoryRepository sportsCategoryRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    @GetMapping("/api/searchevents")
    List<EventProfileDetails> searchEvents(@RequestParam(name = "categoryid") int categoryID,
                                           @RequestParam  @DateTimeFormat(pattern = "dd-MM-yyyy") Date eventDate,
                                           @RequestParam Time time,
                                           @RequestParam String eventCity){

        if(eventCity.equals("")) {
            return eventRepository.findAllByDateAfterAndEventTimeAfterAndSportsCategorySportsCategoryIdOrderByDateAscEventTimeAsc(eventDate, time, categoryID)
                    .stream().limit(10).map(e -> new EventProfileDetails(e)).collect(Collectors.toList());
        }else {
            return eventRepository.findAllByDateAfterAndEventTimeAfterAndSportsCategorySportsCategoryIdAndEventPlaceCityOrderByDateAscEventTimeAsc
                    (eventDate, time, categoryID, eventCity)
                    .stream().limit(10).map(e -> new EventProfileDetails(e)).collect(Collectors.toList());
        }
    }

    @GetMapping("/api/event")
    EventProfileDetails findOneEvent (@RequestParam(name = "eventId") Long eventId) {
        return new EventProfileDetails(eventRepository.findByEventId(eventId));
    }

    @GetMapping("api/myevents/{username}")
    List<EventProfileDetails> getMyEvents(@PathVariable(value = "username") String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return eventRepository.findAllByEventParticipationsIdUser(user)
                .stream().map(event -> new EventProfileDetails(event)).collect(Collectors.toList());

    }

    @GetMapping("/api/counteventsparticipants")
    long countParticipants(@RequestParam Long eventID){
        return eventRepository.countAllByEventParticipationsIdEvent(eventRepository.findByEventId(eventID));
    }

    @GetMapping("api/eventuser")
    String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }

    @PostMapping("api/newEvent")
    public ResponseEntity<?> addNewEvent(@RequestBody NewEvent newEvent) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        CyclePeriod cyclePeriod = cyclePeriodRepository.findByCyclePeriodId(newEvent.getCycleId());
        EventStatus eventStatus = eventStatusRepository.findByEventStatusId(1L);
        EventPlace eventPlace = eventPlaceRepository.findByEventPlaceId(newEvent.getEventPlaceId());
        SportsCategory sportsCategory = sportsCategoryRepository.findBySportsCategoryId(newEvent.getSportCategoryId());

        boolean isCyclic = false;
        if(cyclePeriod != null)
            isCyclic = true;

        Event event = new Event(cyclePeriod, eventStatus, eventPlace, sportsCategory, newEvent.getEventName(),
                newEvent.getEventDescription(), newEvent.getEventDate(), newEvent.getMaxPlayers(), isCyclic,
                newEvent.getEventTime(), newEvent.getEventDuration(), user);

        Event result = eventRepository.save(event);

        event.addParticipator(user, event, 0);
        eventRepository.save(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getEventId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "New event added successfully"));
    }

    @PostMapping("api/updateEvent")
    public ResponseEntity<?> updateEvent(@RequestBody UpdatedEvent updatedEvent) {
        Long eventId = updatedEvent.getEventId();
        Event event = eventRepository.findByEventId(eventId);

        Long cycleId = updatedEvent.getCycleId();
        CyclePeriod cyclePeriod = cyclePeriodRepository.findByCyclePeriodId(cycleId);
        event.setCyclePeriod(cyclePeriod);

        boolean isCyclic = false;
        if(cyclePeriod != null)
            isCyclic = true;

        Long eventPlaceId = updatedEvent.getEventPlaceId();
        EventPlace eventPlace = eventPlaceRepository.findByEventPlaceId(eventPlaceId);
        event.setEventPlace(eventPlace);

        event.setSportsCategory(sportsCategoryRepository.findBySportsCategoryId(updatedEvent.getSportCategoryId()));

        event.setName(updatedEvent.getEventName());
        event.setDescription(updatedEvent.getEventDescription());
        event.setDate(updatedEvent.getEventDate());
        event.setEventTime(updatedEvent.getEventTime());
        event.setMaxPlayers(updatedEvent.getMaxPlayers());
        event.setCyclic(isCyclic);
        event.setEventDuration(updatedEvent.getEventDuration());

        Event result = eventRepository.save(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getEventId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "New event added successfully"));
    }

    @PostMapping("api/cancelEvent/{eventID}")
    public ResponseEntity<?> cancelEvent(@PathVariable(value = "eventID") Long eventID) {
        Event event = eventRepository.findByEventId(eventID);
        event.setEventStatus(eventStatusRepository.findByEventStatusId(2L));

        Event result = eventRepository.save(event);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getEventId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Event canceled successfully"));
    }
}
