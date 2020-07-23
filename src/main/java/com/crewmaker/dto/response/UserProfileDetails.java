package com.crewmaker.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDetails {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String description;

    public UserProfileDetails(String username, String email, String phoneNumber, String description, String name, String surname) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.name = name;
        this.surname = surname;
    }

    public UserProfileDetails(String username, String email, String phoneNumber, String description) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

}