package com.crewmaker.repository;

import com.crewmaker.entity.Event;
import com.crewmaker.entity.User;
import com.crewmaker.dto.response.UserProfileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.crewmaker.dto.response.UserProfileDetails(u.username, u.email, u.phoneNumber, u.description) " +
            "FROM User u where u.username = :username")
    UserProfileDetails findByUsernameUserProfile(@Param("username") String username);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findDistinctByUserParticipationsId_IdEvent(Event event);
}