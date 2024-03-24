package com.kani.webproject.dto;

import lombok.Data;

@Data
public class UserAuthenticationRequestDto {
    private String username;
    private String password;
}
