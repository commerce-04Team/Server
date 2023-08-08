package com.commerce_04.commerce.Repository.user.Entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "User")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "address")
    private String address;
}
