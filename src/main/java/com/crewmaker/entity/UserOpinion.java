package com.crewmaker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="UserOpinion")
public class UserOpinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userOpinionID")
    private int userOpinionId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userAuthorID")
    private User userAuthor;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userAboutID")
    private User userAbout;

    @Column(name="title")
    private String title;

    @Column(name="message")
    private String message;

    @Column(name="grade")
    private int grade;

    public UserOpinion(User userAuthor, User userAbout, String title, String message, int grade) {
        this.userAuthor = userAuthor;
        this.userAbout = userAbout;
        this.title = title;
        this.message = message;
        this.grade = grade;
    }

}
