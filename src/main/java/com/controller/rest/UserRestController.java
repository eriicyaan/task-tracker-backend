package com.controller.rest;

import com.dto.UserReadDto;
import com.dto.response.UserResponse;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public UserResponse getUser(@AuthenticationPrincipal UserDetails user) {
        UserReadDto userReadDto = userService.findUserByUsername(user.getUsername());

        return new UserResponse(userReadDto.getId(), user.getUsername());
    }
}