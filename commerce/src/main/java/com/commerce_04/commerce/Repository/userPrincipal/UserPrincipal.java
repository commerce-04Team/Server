package com.commerce_04.commerce.Repository.userPrincipal;
import com.commerce_04.commerce.Repository.user.Entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "user_principal")
public class UserPrincipal {

    @Id
    @Column(name = "user_principal_id")
    private String userPrincipalId;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "userPrincipal",fetch = FetchType.EAGER)
    private Collection<UserPrincipalRoles> userPrincipalRoles;
}
