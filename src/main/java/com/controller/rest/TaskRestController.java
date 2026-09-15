package com.controller.rest;


import com.dto.TaskReadDto;
import com.dto.response.TaskResponse;
import com.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getTasks(Authentication authentication) {
        List<TaskReadDto> tasks = taskService.findAllTasks(authentication.getName());

        return tasks.stream()
                .map(task -> new TaskResponse(
                        task.getHeader(),
                        task.getBody(),
                        task.getStatus().name(),
                        task.getDoneAt()
                        )
                )
                .toList();
    }
}
