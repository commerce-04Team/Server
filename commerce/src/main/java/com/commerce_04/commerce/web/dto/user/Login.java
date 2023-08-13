package com.commerce_04.commerce.web.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Login {
    private String user_id;
    private String password;
}
