package com.crewmaker.dto.response;

import com.crewmaker.entity.EventPlaceOpinion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventPlaceOpinionDetails {
    private String userAuthor;
    private String title;
    private String message;
    private int grade;

    public EventPlaceOpinionDetails(EventPlaceOpinion opinion) {
        this.userAuthor = opinion.getUserAuthor().getUsername();
        this.title = opinion.getTitle();
        this.message = opinion.getMessage();
        this.grade = opinion.getGrade();
    }
}