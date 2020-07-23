package com.crewmaker.repository;

import com.crewmaker.entity.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {
    @Modifying
    @Query("UPDATE UserProfileImage u SET u.name = ?1, u.type = ?2, u.binaryData = ?3 WHERE u.imageId = ?4")
    void setUserProfileImageById(String name, String type, byte[] binaryData, Long imageId);
}