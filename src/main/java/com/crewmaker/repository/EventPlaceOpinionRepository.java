package com.crewmaker.repository;

import com.crewmaker.entity.EventPlace;
import com.crewmaker.entity.EventPlaceOpinion;
import com.crewmaker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventPlaceOpinionRepository extends JpaRepository<EventPlaceOpinion,Long> {
    List<EventPlaceOpinion> findAllByEventPlaceEventPlaceId(Long id);
    EventPlaceOpinion findByEventPlaceAndUserAuthorUsername(EventPlace eventPlace, String current);
    EventPlaceOpinion findByEventPlaceAndUserAuthor(EventPlace eventPlace, User current);
}