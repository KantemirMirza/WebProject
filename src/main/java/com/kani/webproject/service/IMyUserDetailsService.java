package com.kani.webproject.service;

import com.kani.webproject.dto.SignupRequest;
import com.kani.webproject.dto.UserDto;
import com.kani.webproject.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IMyUserDetailsService extends UserDetailsService {
    Optional<User> findByEmail(String username);

    UserDto createUser(SignupRequest register);

    Boolean hasUserWithEmail(String email);

    void createAdminAccount();

}
