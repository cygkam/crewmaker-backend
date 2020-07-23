package com.crewmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="cyclePeriod")
public class CyclePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cyclePeriodID")
    private Long cyclePeriodId;

    @Column(name="cycleType")
    private String cycleType;

    @Column(name="cycleLength")
    private int cycleLength;

    @JsonIgnore
    @OneToMany(mappedBy="cyclePeriod", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Event> cyclePeriodEvents;

}