package com.crewmaker.dto.response;

import lombok.Data;
import com.crewmaker.entity.Event;
import java.sql.Time;
import java.util.Date;

@Data
public class EventProfileDetails {
    private Long eventID;
    private String eventName;
    private Date eventDate;
    private Time eventTime;
    private String eventDescription;
    private boolean isCyclic;
    private String cycleType;
    private int cycleLength;
    private int maxPlayers;
    private String eventPlaceName;
    private String eventPlaceDescription;
    private String eventPlaceStreetName;
    private String eventPlaceStreetNumber;
    private String eventPlacePostCode;
    private String eventPlaceCity;
    private String eventSportName;
    private int eventSportID;
    private Time eventDuration;
    private Long eventPlaceID;
    private String userInitiator;
    private String eventStatus;
    private Long cycleId;

    public EventProfileDetails(Event event){
        eventID = event.getEventId();
        eventName = event.getName();
        eventDate = event.getDate();
        eventTime = event.getEventTime();
        eventDescription = event.getDescription();
        isCyclic = event.isCyclic();
        if(isCyclic) {
            cycleType = event.getCyclePeriod().getCycleType();
            cycleLength = event.getCyclePeriod().getCycleLength();
            cycleId = event.getCyclePeriod().getCyclePeriodId();
        }
        eventPlaceDescription = event.getEventPlace().getDescription();
        eventPlacePostCode = event.getEventPlace().getPostCode();
        eventTime.setTime(event.getEventTime().getTime() - 3600000);
        this.eventPlaceID = event.getEventPlace().getEventPlaceId();
        eventPlaceName = event.getEventPlace().getName();
        eventPlaceStreetName = event.getEventPlace().getStreet();
        eventPlaceCity = event.getEventPlace().getCity();
        maxPlayers = event.getMaxPlayers();
        eventSportName = event.getSportsCategory().getSportCategoryName();
        eventSportID = event.getSportsCategory().getSportsCategoryId();
        eventPlaceStreetNumber = event.getEventPlace().getStreetNumber();
        eventDuration = event.getEventDuration();
        userInitiator = event.getUserInitiator().getUsername();
        eventStatus = event.getEventStatus().getStatusName();
    }

}