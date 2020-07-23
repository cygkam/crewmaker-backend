package com.crewmaker.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="userProfileImage")
public class UserProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="imageID")
    private Long imageId;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Lob
    @Type(type="org.hibernate.type.ImageType")
    @Column(name="binaryData")
    private byte[] binaryData;

    public UserProfileImage(String originalFilename, String contentType, byte[] compressBytes) {
        this.name = originalFilename;
        this.type = contentType;
        this.binaryData = compressBytes;
    }
}