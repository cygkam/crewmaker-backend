package com.crewmaker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewEventPlaceOpinion {
    private Long eventPlaceAbout;
    @NotBlank
    private String title;
    @NotBlank
    private String message;
    private int grade;
}