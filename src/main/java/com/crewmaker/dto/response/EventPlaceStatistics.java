package com.crewmaker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventPlaceStatistics {
    int allEventsCount;
    int passedEventsCount;
    int incomingEventsCount;
}
