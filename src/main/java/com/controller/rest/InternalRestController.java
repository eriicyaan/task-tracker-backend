package com.controller.rest;


import com.dto.UserReadDto;
import com.dto.response.UserResponse;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/internal")
@RequiredArgsConstructor
public class InternalRestController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        List<UserReadDto> users = userService.getAllUsers();

        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername()))
                .toList();
    }
}
