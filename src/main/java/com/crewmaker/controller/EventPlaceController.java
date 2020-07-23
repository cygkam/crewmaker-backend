package com.crewmaker.controller;

import com.crewmaker.dto.request.NewEventPlace;
import com.crewmaker.dto.response.*;
import com.crewmaker.entity.*;
import com.crewmaker.repository.*;
import com.crewmaker.service.EventPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EventPlaceController {

    @Autowired
    EventPlaceRepository eventPlaceRepository;
    @Autowired
    CyclePeriodRepository cyclePeriodRepository;
    @Autowired
    EventPlaceService eventPlaceService;

    @PostMapping("/newEventPlace")
    public ResponseEntity<?> addNewEventPlace(@RequestBody NewEventPlace newEventPlace) {
        boolean successfullyAdded = eventPlaceService.addEventPlace(newEventPlace);
        return ResponseEntity.ok().body(new ApiResponse(successfullyAdded, successfullyAdded ? "New event place added successfully" : "Error, couldn't add event place"));
    }

    @GetMapping("/getEventPlace")
    Page<EventPlaceProfileLongDetails> getEventPlace(@RequestParam(required = true, defaultValue = "0", name = "activePage") int activePage,
                                            @RequestParam(required = true, defaultValue = "10" , name = "size") int size,
                                            @RequestParam(required = false, name = "isArchived") Boolean isArchived,
                                            @RequestParam(required = false, name = "isAccepted") Boolean isAccepted,
                                            @RequestParam(required = false, name = "city") String city,
                                            @RequestParam(required = false, name = "sort") String sort,
                                            @RequestParam(required = false, defaultValue = "ASC" ,name = "dir") String dir){
        return eventPlaceService.getEventPlaces(activePage, size, isArchived, isAccepted, city,  sort, dir);
    }

    @GetMapping("/acceptEventPlace")
    ResponseEntity<?> acceptEventPlace(@RequestParam(required = true, name = "eventPlaceID") Long eventPlaceID){
        return ResponseEntity.ok().body(eventPlaceService.acceptEventPlace(eventPlaceID));
    }

    @GetMapping("/archiveEventPlace")
    ResponseEntity<?> archiveEventPlace(@RequestParam(required = true, name = "eventPlaceID") Long eventPlaceID,
                                       @RequestParam(required = true, name = "currentArchiveStatus") boolean currentArchiveStatus){
        boolean successfullyChanged = eventPlaceService.changeArchiveStatus(eventPlaceID, currentArchiveStatus);
        return ResponseEntity.ok().body(new ApiResponse(successfullyChanged, successfullyChanged ? "Event place archive status has been changed" : "Error, couldn't change arhive"));
    }

    @GetMapping("/countEventPlaceEvents")
    ResponseEntity<?> getEventPlaceEventsCount(@RequestParam(required = true, name = "eventPlaceID") Long eventPlaceID ){
        return ResponseEntity.ok().body(eventPlaceService.countEventPlaceStatistics(eventPlaceID));
    }

    @GetMapping("/eventPlaces")
    List<EventPlaceProfileShortDetails> getEventPlaces() {
        return eventPlaceRepository.findTop20ByIsAcceptedIsTrue().stream().limit(20).map(eventPlace ->  new EventPlaceProfileShortDetails(eventPlace)).collect(Collectors.toList());
    }

    @GetMapping("/eventPlace")
    EventPlaceProfileShortDetails getEventPlace(@RequestParam Long eventPlaceID) {
        return new EventPlaceProfileShortDetails(eventPlaceRepository.findByEventPlaceId(eventPlaceID));
    }

    @GetMapping("/eventPlacesByCategoryAndCity")
    List<EventPlaceProfileShortDetails> getEventPlacesBySportCategoryCity(@RequestParam int sportCategoryId,
                                                                          @RequestParam String eventCity) {
        return eventPlaceRepository.findEventPlaceByCityAndSportCategory(sportCategoryId, eventCity).stream().collect(Collectors.toList());
    }

    @GetMapping("/cyclics")
    List<CyclePeriod> getCyclics() {
        return cyclePeriodRepository.findAll();
    }
}