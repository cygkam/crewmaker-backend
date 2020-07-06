package com.crewmaker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventPlaceAcceptance {
    private Boolean success;
    private Boolean isAccepted;
    private String userAcceptingUsername;
    private String message;

    public EventPlaceAcceptance(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}

