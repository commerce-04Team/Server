package com.commerce_04.commerce.web.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {
    private String email;
    private String password;
    private String name;
}
