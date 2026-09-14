package com.service;

import com.dto.UserCreateEditDto;
import com.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    public String signUp(UserCreateEditDto user) {
        String email = user.getEmail();

        if(userService.findUserByEmail(email) != null) {
            throw new UserAlreadyExistsException(String.format("user with email {} already exists", email));
        }

        userService.create(user);
        return jwtService.generateJwtToken(user.getEmail());
    }
}
