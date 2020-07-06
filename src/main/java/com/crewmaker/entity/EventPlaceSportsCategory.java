package com.crewmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="EventPlaceSportsCategory")
public class EventPlaceSportsCategory {

    @EmbeddedId
    EventPlaceSportsCategoryId id;

    @Column(name="maxEventsSimultaneously")
    private Integer maxEventsSimultaneously;

    public EventPlaceSportsCategory(EventPlace eventPlace, SportsCategory sportsCategory) {
        this.id = new EventPlaceSportsCategoryId(eventPlace, sportsCategory);
    }

}

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class EventPlaceSportsCategoryId implements Serializable {

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="eventPlaceID")
    private EventPlace eventPlace;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="sportsCategoryID")
    private SportsCategory sportsCategory;

}