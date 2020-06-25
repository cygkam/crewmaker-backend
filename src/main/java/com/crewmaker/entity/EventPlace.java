package com.crewmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="EventPlace")
public class EventPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventPlaceID")
    private int eventPlaceId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userAcceptingID")
    private User userAccepting;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userRequestingID")
    private User userRequesting;

    @Column(name="eventPlaceName")
    private String name;

    @Column(name="eventPlaceDescription")
    private String description;

    @Column(name="eventPlaceCity")
    private String city;

    @Column(name="eventPlacePostCode")
    private String postCode;

    @Column(name="eventPlaceStreet")
    private String street;

    @Column(name="eventPlaceStreetNumber")
    private String streetNumber;

    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="photoLink")
    @JsonIgnore
    private EventPlaceImage photoLink;

    @Column(name="isAccepted")
    private Boolean isAccepted;

    @Column(name="isArchived")
    private Boolean isArchived;

    @OneToMany(mappedBy="eventPlace", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<EventPlaceOpinion> eventPlaceEventPlaceOpinions;

    @OneToMany(mappedBy="id.eventPlace", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<EventPlaceSportsCategory> eventPlaceSportsCategories;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "EventPlaceSportsCategory",
            joinColumns = { @JoinColumn(name = "eventPlaceID") },
            inverseJoinColumns = { @JoinColumn(name = "sportsCategoryID") }
    )
    Set<SportsCategory> sportsCategory = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy="eventPlace", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Event> eventPlaceEvents;

    public EventPlace(User userRequesting, String name, String description, String city,
                      String postCode, String street, String streetNumber, EventPlaceImage photoLink) {
        this.userRequesting = userRequesting;
        this.name = name;
        this.description = description;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.streetNumber = streetNumber;
        this.photoLink = photoLink;
        this.isAccepted = false;
        this.isArchived = false;
    }

    public String getUserAcceptingUsername() {
        return userAccepting.getUsername();
    }

    public String getUserRequestingUsername() {
        return userRequesting.getUsername();
    }

}