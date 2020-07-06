package com.crewmaker.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class NewUserOpinion {

        private String opinionAuthorName;

        private String userAbout;

        @NotBlank
        private String title;

        @NotBlank
        private String message;

        private int grade;
}
