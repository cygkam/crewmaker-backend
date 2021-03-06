package com.crewmaker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdatedEvent {

    @NotBlank
    private Long eventId;
    private Long cycleId;
    @NotBlank
    private Long eventPlaceId;
    @NotBlank
    private int sportCategoryId;
    @NotBlank
    private String eventName;
    @NotBlank
    private String eventDescription;
    @NotBlank
    private Date eventDate;
    @NotBlank
    private Time eventTime;
    @NotBlank
    private int maxPlayers;
    @NotBlank
    private boolean isCyclic;
    @NotBlank
    private Time eventDuration;
}