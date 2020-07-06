package com.crewmaker.repository;

import com.crewmaker.dto.response.SportsCategoryDetails;
import com.crewmaker.entity.SportsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SportsCategoryRepository extends JpaRepository<SportsCategory,Integer> {
    @Query("SELECT new com.crewmaker.dto.response.SportsCategoryDetails(sc.sportsCategoryId, sc.sportCategoryName) " +
            "FROM SportsCategory sc " +
            "JOIN sc.sportsCategoryEventPlaces scep " +
            "JOIN scep.id.eventPlace ep " +
            "WHERE ep.eventPlaceId = :eventPlaceID")
    List<SportsCategoryDetails> findByEventPlaceId(@Param("eventPlaceID") Long eventPlaceID);
    List<SportsCategory> findAll();
    SportsCategory findBySportsCategoryId(int id);
}