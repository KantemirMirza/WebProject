package com.kani.webproject.dto;

import com.kani.webproject.enumaration.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String userName;
    private UserRole userRole;
}
