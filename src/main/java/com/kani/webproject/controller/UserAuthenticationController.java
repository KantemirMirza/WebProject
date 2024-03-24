package com.kani.webproject.controller;

import com.kani.webproject.dto.SignupRequest;
import com.kani.webproject.dto.UserAuthenticationRequestDto;
import com.kani.webproject.dto.UserDto;
import com.kani.webproject.entity.User;
import com.kani.webproject.jwt.JwtUtil;
import com.kani.webproject.service.IMyUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserAuthenticationController {
    public static final String HEADER_STRING = "Bearer";
    public static final String TOKEN_PREFIX = "Authorization";
    private final AuthenticationManager authenticationManager;
    private final IMyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody SignupRequest signupRequest){
        if(myUserDetailsService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User, Already Exist", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = myUserDetailsService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody UserAuthenticationRequestDto authRequest,
                                          HttpServletResponse authResponse) throws IOException, JSONException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
        Optional<User> user = myUserDetailsService.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        if(user.isPresent()){
            authResponse.getWriter().write(new JSONObject()
                    .put("UserId: ", user.get().getId())
                    .put("Role: ", user.get().getUserRole())
                    .toString()
            );
            authResponse.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

}
