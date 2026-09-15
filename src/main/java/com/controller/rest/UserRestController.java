package com.controller.rest;

import com.dto.UserReadDto;
import com.dto.response.UserResponse;
import com.service.UserService;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public UserResponse getUser(Authentication authentication) {
        String username = authentication.getName();

        UserReadDto userReadDto = userService.findUserByUsername(username);

        return new UserResponse(userReadDto.getId(), username);
    }
}