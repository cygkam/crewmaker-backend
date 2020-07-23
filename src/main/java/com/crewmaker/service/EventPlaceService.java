package com.crewmaker.service;

import com.crewmaker.dto.request.NewEventPlace;
import com.crewmaker.dto.response.EventPlaceAcceptance;
import com.crewmaker.dto.response.EventPlaceProfileLongDetails;
import com.crewmaker.dto.response.EventPlaceStatistics;
import com.crewmaker.entity.*;
import com.crewmaker.exception.ResourceNotFoundException;
import com.crewmaker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class EventPlaceService {

    @Autowired
    EventPlaceRepository eventPlaceRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SportsCategoryRepository sportsCategoryRepository;
    @Autowired
    EventPlaceSportsCategoryRepository eventPlaceSportsCategoryRepository;
    @Autowired
    ImageService imageService;

    public Page<EventPlaceProfileLongDetails> getEventPlaces(int activePage, int size, Boolean isArchived, Boolean isAccepted, String city, String sort, String dir) {
        Page<EventPlace> page = eventPlaceRepository.findAll(EventPlaceSpecification.getEventPlaceByAccepted(isAccepted, isArchived, city),
                PageRequest.of(activePage - 1, size, Sort.by(Sort.Direction.valueOf(dir), sort)));

        Page<EventPlaceProfileLongDetails> eventPlaceResponse = page.map(new Function<EventPlace, EventPlaceProfileLongDetails>() {
            @Override
            public EventPlaceProfileLongDetails apply(EventPlace eventPlace) {
                return new EventPlaceProfileLongDetails(eventPlace);
            }
        });

        return eventPlaceResponse;
    }

    public EventPlaceStatistics countEventPlaceStatistics(Long eventPlaceID) {
        EventPlace eventPlace = eventPlaceRepository.findById(eventPlaceID)
                .orElseThrow(() -> new ResourceNotFoundException("EventPlace", "eventPlaceId", eventPlaceID));
        Date currentDate = new Date();

        int calculate = eventRepository.countAllByEventPlace(eventPlace);
        int calculateBefore = eventRepository.countAllByEventPlaceAndDateBefore(eventPlace, currentDate);
        int calculateAfter = eventRepository.countAllByEventPlaceAndDateAfter (eventPlace, currentDate);

        return new EventPlaceStatistics(calculate, calculateBefore, calculateAfter);
    }

    public boolean addEventPlace(NewEventPlace newEventPlace) {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

            EventPlaceImage newImg = null;

            if(newEventPlace.getEventPlaceImage() != null) {
                String prefixType = newEventPlace.getEventPlaceImage().split(";")[0];
                String type = prefixType.split(":")[1];
                String base64Image = newEventPlace.getEventPlaceImage().split(",")[1];
                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
                newImg = new EventPlaceImage("newEventPlaceImage", type,
                        ImageService.compressBytes(imageBytes));
            }

            EventPlace eventPlace = new EventPlace(user, newEventPlace.getEventPlaceName(), newEventPlace.getEventPlaceDescription(), newEventPlace.getEventPlaceCity(),
                    newEventPlace.getEventPlacePostalCode(), newEventPlace.getEventPlaceStreet(), newEventPlace.getEventPlaceStreetNumber(), newImg);

            EventPlace result = eventPlaceRepository.save(eventPlace);

            for(int sportCategoryId: newEventPlace.getSportsCategory()){
                SportsCategory sportsCategory = sportsCategoryRepository.findById(sportCategoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Sports Category", "id", sportCategoryId));

                EventPlaceSportsCategory newEventPlaceSportsCategory = new EventPlaceSportsCategory(result, sportsCategory);
                eventPlaceSportsCategoryRepository.save(newEventPlaceSportsCategory);
            }
        }catch(ResourceNotFoundException e){
            return false;
        }
        return true;
    }

    public EventPlaceAcceptance acceptEventPlace(Long eventPlaceID) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        EventPlace eventPlace;
        try{
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
            eventPlace = eventPlaceRepository.findById(eventPlaceID)
                    .orElseThrow(() -> new ResourceNotFoundException("EventPlace", "eventPlaceId", eventPlaceID));
            eventPlace.setIsAccepted(true);
            eventPlace.setUserAccepting(user);
            eventPlaceRepository.save(eventPlace);
        }catch(ResourceNotFoundException e){
            return new EventPlaceAcceptance(false,  false,
                    username, "Error, event place couldn't be accepted");
        }
            return new EventPlaceAcceptance(true,  eventPlace.getIsAccepted(),
                    username, "Event place successfully accepted");
    }

    public boolean changeArchiveStatus(Long eventPlaceID, boolean currentArchiveStatus) {
        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));


            EventPlace eventPlace = eventPlaceRepository.findById(eventPlaceID)
                    .orElseThrow(() -> new ResourceNotFoundException("EventPlace", "eventPlaceId", eventPlaceID));
            eventPlace.setIsArchived(!currentArchiveStatus);
            eventPlaceRepository.save(eventPlace);
        }catch(ResourceNotFoundException e){
            return false;
        }
        return true;
    }
}
