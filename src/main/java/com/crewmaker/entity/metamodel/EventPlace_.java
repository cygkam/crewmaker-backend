package com.crewmaker.entity.metamodel;

import com.crewmaker.entity.EventPlace;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EventPlace.class)
public abstract class EventPlace_ {
    public static volatile SingularAttribute<EventPlace, Long> eventPlaceId;
    public static volatile SingularAttribute<EventPlace, String> name;
    public static volatile SingularAttribute<EventPlace, String> city;
    public static volatile SingularAttribute<EventPlace, Boolean> isAccepted;
    public static volatile SingularAttribute<EventPlace, Boolean> isArchived;

    public static final String EVENT_PACE_ID = "eventPlaceId";
    public static final String NAME = "name";
    public static final String CITY = "city";
    public static final String IS_ARCHIVED = "isArchived";
    public static final String IS_ACCEPTED = "isAccepted";
}