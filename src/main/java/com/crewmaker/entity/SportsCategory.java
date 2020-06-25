package com.crewmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="SportsCategory")
public class SportsCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sportsCategoryID")
    private int sportsCategoryId;

    @Column(name="sportCategoryName")
    private String sportCategoryName;

    @Column(name="defaultPlayersNumber")
    private int defaultPlayersNumber;

    @JsonIgnore
    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="eventplacesportscategory",
            joinColumns=@JoinColumn(name="sportsCategoryID"),
            inverseJoinColumns=@JoinColumn(name="eventPlaceID"))
    private List<EventPlace> eventPlaces;

    @JsonIgnore
    @OneToMany(mappedBy="sportsCategory", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Event> sportsCategoryEvents;

    @JsonIgnore
    @OneToMany(mappedBy="id.sportsCategory", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<EventPlaceSportsCategory> sportsCategoryEventPlaces;

    public SportsCategory(String sportCategoryName, int defaultPlayersNumber) {
        this.sportCategoryName = sportCategoryName;
        this.defaultPlayersNumber = defaultPlayersNumber;
    }

}
