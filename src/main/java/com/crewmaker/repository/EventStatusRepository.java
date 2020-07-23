package com.crewmaker.repository;

import com.crewmaker.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus,Long> {
    EventStatus findByEventStatusId(Long id);
}