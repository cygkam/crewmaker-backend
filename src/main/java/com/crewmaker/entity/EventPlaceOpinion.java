package com.crewmaker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="eventPlaceOpinion")
public class EventPlaceOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventPlaceOpinionID")
    private Long eventPlaceOpinionId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="eventPlaceID")
    private EventPlace eventPlace;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userID")
    private User userAuthor;

    @Column(name="title")
    private String title;

    @Column(name="message")
    private String message;

    @Column(name="grade")
    private int grade;

    public EventPlaceOpinion(EventPlace eventPlace, User userAuthor, String title, String message, int grade) {
        this.eventPlace = eventPlace;
        this.userAuthor = userAuthor;
        this.title = title;
        this.message = message;
        this.grade = grade;
    }

}