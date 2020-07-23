package com.crewmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="participation")
public class Participation {

    @EmbeddedId
    ParticipationId id;

    @Column(name="queuePosition")
    private Integer queuePosition;

    public Participation(User user, Event event, Integer queuePosition) {
        this.id = new ParticipationId(user, event);
        this.queuePosition = queuePosition;
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class ParticipationId  implements Serializable {

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userID")
    private User user;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="eventID")
    private Event event;

}