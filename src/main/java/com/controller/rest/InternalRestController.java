package com.controller.rest;


import com.dto.TaskReadDto;
import com.dto.UserReadDto;
import com.dto.response.TaskResponse;
import com.dto.response.UserResponse;
import com.mapper.TaskMapper;
import com.mapper.TaskReadMapper;
import com.service.TaskService;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/internal/backend")
@RequiredArgsConstructor
public class InternalRestController {

    private final UserService userService;
    private final TaskService taskService;
    private final TaskReadMapper taskReadMapper;

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        List<UserReadDto> users = userService.getAllUsers();

        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername()))
                .toList();
    }



    @GetMapping("/tasks/{id}")
    public List<TaskResponse> getAllTasks(@PathVariable UUID id) {
        List<TaskReadDto> tasks = taskService.findAllTasks(id);

        return tasks.stream()
                .map(taskReadMapper::map)
                .toList();
    }
}
