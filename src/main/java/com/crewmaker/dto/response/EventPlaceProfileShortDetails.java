package com.crewmaker.dto.response;

import com.crewmaker.entity.EventPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventPlaceProfileShortDetails {
    private Long eventPlaceId;
    private String name;
    private String city;
    private String postCode;
    private String street;
    private String streetNumber;

    public EventPlaceProfileShortDetails(EventPlace eventPlace) {
        this.eventPlaceId = eventPlace.getEventPlaceId();
        this.name = eventPlace.getName();
        this.city = eventPlace.getCity();
        this.postCode = eventPlace.getPostCode();
        this.street = eventPlace.getStreet();
        this.streetNumber = eventPlace.getStreetNumber();
    }
}