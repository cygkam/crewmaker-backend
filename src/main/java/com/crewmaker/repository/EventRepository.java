package com.crewmaker.repository;

import com.crewmaker.entity.Event;
import com.crewmaker.entity.EventPlace;
import com.crewmaker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByEventId(Long id);
    List<Event> findAllByEventParticipationsIdUser(User user);
    Long countAllByEventParticipationsIdEvent(Event event);
    List<Event> findAllByDateAfterAndAndSportsCategorySportsCategoryIdOrderByDate(Date date, int SportCategoryID);
    List<Event> findAllByDateAfterAndEventTimeAfterAndSportsCategorySportsCategoryIdOrderByDateAscEventTimeAsc(
            Date eventDate, Time eventTime, int SportCategoryID);
    int countAllByEventPlace(EventPlace eventPlace);
    int countAllByEventPlaceAndDateAfter(EventPlace eventPlace, Date date);
    int countAllByEventPlaceAndDateBefore(EventPlace eventPlace, Date date);
    List<Event> findAllByDateAfterAndEventTimeAfterAndSportsCategorySportsCategoryIdAndEventPlaceCityOrderByDateAscEventTimeAsc(Date eventDate,
                                                                                                                                Time eventTime,
                                                                                                                                int SportCategoryID,
                                                                                                                                String eventPlaceCity);
}