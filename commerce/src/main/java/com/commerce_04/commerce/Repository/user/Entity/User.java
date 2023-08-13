package com.commerce_04.commerce.Repository.user.Entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickName;
    @Column(name = "address")
    private String address;
}
