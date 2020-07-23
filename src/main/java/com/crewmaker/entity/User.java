package com.crewmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userID")
    private Long userId;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;
    
    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="archived")
    private boolean archived;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="imageID")
    @JsonIgnore
    private UserProfileImage userProfileImage;

    @Column(name="description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRoles",
            joinColumns = @JoinColumn(name = "userPermittedID"),
            inverseJoinColumns = @JoinColumn(name = "roleAssignedID"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy="userAccepting", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<EventPlace> userAcceptingEventPlaces;

    @OneToMany(mappedBy="userRequesting", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<EventPlace> userRequestingEventPlaces;

    @OneToMany(mappedBy="userAuthor", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<UserOpinion> userAuthorUserOpinions;

    @OneToMany(mappedBy="userAbout", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<UserOpinion> userAboutUserOpinions;

    @OneToMany(mappedBy="userAuthor", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<EventPlaceOpinion> userAuthorEventPlaceOpinions;

    @OneToMany(mappedBy="id.user", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Participation> userParticipations;

    @OneToMany(mappedBy="userInitiator", cascade= {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private Set<Event> userEvents;

    public User(String username, String email, String password, String name, String surname, boolean archived, String phoneNumber,
                boolean enabled, String photoLink, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.archived = archived;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.description = description;
    }

}