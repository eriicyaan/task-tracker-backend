package com.service;

import com.dto.UserCreateEditDto;
import com.dto.UserSignInDto;
import com.exception.UserAlreadyExistsException;
import com.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public String signUp(UserCreateEditDto user) {
        String email = user.getUsername();

        if(userService.findUserByUsername(email) != null) {
            throw new UserAlreadyExistsException(String.format("user with email %s already exists", email));
        }

        userService.create(user);
        return jwtService.generateJwtToken(user.getUsername());
    }


    public String signIn(UserSignInDto user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        return jwtService.generateJwtToken(user.getUsername());
    }
}
