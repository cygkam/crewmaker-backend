package com.crewmaker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="eventPlaceImage")
public class EventPlaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventPlaceImageID")
    private Long eventPlaceImageID;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @Lob
    @Type(type="org.hibernate.type.ImageType")
    @Column(name="binaryData")
    private byte[] binaryData;

    public EventPlaceImage(String originalFilename, String contentType, byte[] compressBytes) {
        this.name = originalFilename;
        this.type = contentType;
        this.binaryData = compressBytes;
    }
}