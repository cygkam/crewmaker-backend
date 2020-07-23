package com.crewmaker.dto.response;

import com.crewmaker.entity.EventPlace;
import com.crewmaker.entity.EventPlaceSportsCategory;
import com.crewmaker.entity.SportsCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EventPlaceProfileLongDetails {
    private Long eventPlaceID;
    private String eventPlaceName;
    private String eventPlaceDescription;
    private String eventPlaceStreetName;
    private String eventPlaceStreetNumber;
    private String eventPlacePostCode;
    private String eventPlaceCity;
    private Set<SportsCategory> sportsCategories;
    private String userRequestingUsername;
    private String userAcceptingUsername;
    private Boolean isAccepted;
    private Boolean isArchived;

    public EventPlaceProfileLongDetails(EventPlace eventPlace){
        eventPlaceID = eventPlace.getEventPlaceId();
        eventPlaceName = eventPlace.getName();
        eventPlaceDescription = eventPlace.getDescription();
        eventPlaceStreetName = eventPlace.getStreet();
        eventPlaceStreetNumber = eventPlace.getStreetNumber();
        eventPlacePostCode = eventPlace.getPostCode();
        eventPlaceCity = eventPlace.getCity();
        sportsCategories = eventPlace.getSportsCategory();
        userRequestingUsername = eventPlace.getUserRequestingUsername();
        isAccepted = eventPlace.getIsAccepted();

        if(isAccepted == false)
            userAcceptingUsername = "";
        else
            userAcceptingUsername = eventPlace.getUserAcceptingUsername();

        isArchived = eventPlace.getIsArchived();
    }

}