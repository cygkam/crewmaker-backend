package com.crewmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventID")
    private int eventId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="cyclePeriodID")
    private CyclePeriod cyclePeriod;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="eventStatusID")
    private EventStatus eventStatus;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="eventPlaceID")
    private EventPlace eventPlace;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="sportsCategoryID")
    private SportsCategory sportsCategory;

    @OneToMany(mappedBy="id.event", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Participation> eventParticipations;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userID")
    private User userInitiator;

    @Column(name="eventName")
    private String name;

    @Column(name="eventDescription")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name="eventDate")
    private Date date;

    @Column(name="eventMaxPlayers")
    private int maxPlayers;

    @Column(name="isCyclic")
    private boolean isCyclic;

    @Column(name="eventTime")
    private Time eventTime;

    @Column(name="eventDuration")
    private Time eventDuration;

    public Event(CyclePeriod cyclePeriod, EventStatus eventStatus, EventPlace eventPlace, SportsCategory sportsCategory,
                 String name, String description, Date date, int maxPlayers, boolean isCyclic, Time eventTime,
                 Time eventDuration, User userInitiator) {
        this.cyclePeriod = cyclePeriod;
        this.eventStatus = eventStatus;
        this.eventPlace = eventPlace;
        this.sportsCategory = sportsCategory;
        this.name = name;
        this.description = description;
        this.date = date;
        this.maxPlayers = maxPlayers;
        this.isCyclic = isCyclic;
        this.eventTime = eventTime;
        this.eventDuration = eventDuration;
        this.userInitiator = userInitiator;
    }

    public void addParticipator(User user, Event event, Integer queuePosition) {
        if (eventParticipations == null) {
            eventParticipations = new HashSet<>();
        }
        Participation participation = new Participation(user, event, queuePosition);
        eventParticipations.add(participation);
    }
}