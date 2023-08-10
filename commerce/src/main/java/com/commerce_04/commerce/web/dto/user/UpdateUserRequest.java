package com.commerce_04.commerce.web.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    private String email;
    private String nickName;
    private String name;
    private String address;
}
