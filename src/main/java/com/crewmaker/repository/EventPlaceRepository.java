package com.crewmaker.repository;

import com.crewmaker.dto.response.EventPlaceProfileShortDetails;
import com.crewmaker.entity.EventPlace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventPlaceRepository extends JpaRepository<EventPlace, Long>, JpaSpecificationExecutor<EventPlace> {
    Page<EventPlace> findAllByIsAcceptedIsFalse(Pageable pageable);
    Page<EventPlace> findAllByIsAcceptedIsTrue(Pageable pageable);
    Page<EventPlace> findAllByIsArchivedIsFalse(Pageable pageable);
    Page<EventPlace> findAllByIsArchivedIsTrue(Pageable pageable);
    Page<EventPlace> findAllByIsAcceptedIsFalseAndCityIgnoreCaseContaining(String city,Pageable pageable);
    Page<EventPlace> findAllByIsAcceptedIsTrueAndCityIgnoreCaseContaining(String city, Pageable pageable);
    Page<EventPlace> findAllByIsArchivedIsFalseAndCityIgnoreCaseContaining(String city, Pageable pageable);
    Page<EventPlace> findAllByIsArchivedIsTrueAndCityIgnoreCaseContaining(String city, Pageable pageable);
    Page<EventPlace> findAllByCityIgnoreCaseContaining(String city, Pageable pageable);
    EventPlace findByEventPlaceId(Long id);
    List<EventPlace> findAll();
    List<EventPlace> findTop20ByIsAcceptedIsTrue();
    @Query("SELECT new com.crewmaker.dto.response.EventPlaceProfileShortDetails(ep.eventPlaceId, ep.name, ep.city, " +
            "ep.postCode, ep.street, ep.streetNumber) " +
            "FROM EventPlace ep " +
            "JOIN ep.eventPlaceSportsCategories epsc " +
            "WHERE ep.city = :eventCity AND epsc.id.sportsCategory.sportsCategoryId = :sportCategoryId AND ep.isArchived = 0")
    List<EventPlaceProfileShortDetails> findEventPlaceByCityAndSportCategory(@Param("sportCategoryId") int sportCategoryId, @Param("eventCity") String eventCity);
}