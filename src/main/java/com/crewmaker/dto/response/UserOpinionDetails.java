package com.crewmaker.dto.response;


import com.crewmaker.entity.UserOpinion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOpinionDetails {
    private Long userOpinionID;
    private String opinionAuthorName;
    private String title;
    private String message;
    private int grade;

    public UserOpinionDetails(UserOpinion opinion) {
        this.userOpinionID = opinion.getUserOpinionId();
        this.opinionAuthorName = opinion.getUserAuthor().getUsername();
        this.title = opinion.getTitle();
        this.message = opinion.getMessage();
        this.grade = opinion.getGrade();
    }

}