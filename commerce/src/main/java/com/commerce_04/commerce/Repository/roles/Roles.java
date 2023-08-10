package com.commerce_04.commerce.Repository.roles;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "roles")
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "name")
    private String name;

    public Roles(String name) {
        this.name = name;
    }
}
