package com.crewmaker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="userRoles")
public class UserRoles {

    @EmbeddedId
    UserRolesId id;

    public UserRoles(UserRolesId id) {
        this.id = id;
    }

    public UserRoles() {
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
class UserRolesId implements Serializable {

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="userPermittedID")
    private User userPermittedId;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="roleAssignedID")
    private Role roleAssignedId;

}