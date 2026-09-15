package com.controller.rest;


import com.dto.TaskCreateDto;
import com.dto.TaskEditDto;
import com.dto.TaskReadDto;
import com.dto.response.TaskResponse;
import com.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getTasks(Authentication authentication) {
        List<TaskReadDto> tasks = taskService.findAllTasks(authentication.getName());


        return tasks.stream()
                .map(task ->
                        TaskResponse.builder()
                                .id(task.getId())
                                .header(task.getHeader())
                                .body(task.getBody())
                                .status(task.getStatus().name())
                                .doneAt(task.getDoneAt())
                                .build()
                )
                .toList();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@PathVariable UUID id) {
        TaskReadDto task = taskService.findTaskById(id);

        return TaskResponse.builder()
                .id(task.getId())
                .header(task.getHeader())
                .body(task.getBody())
                .status(task.getStatus().name())
                .doneAt(task.getDoneAt())
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody @Validated TaskCreateDto task,
                                   Authentication authentication) {
        TaskReadDto createdTask = taskService.createTask(task, authentication.getName());

        return TaskResponse.builder()
                .id(createdTask.getId())
                .header(createdTask.getHeader())
                .body(createdTask.getBody())
                .status(createdTask.getStatus().name())
                .doneAt(createdTask.getDoneAt())
                .build();

    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse updateTask(@PathVariable UUID id,
                                   @RequestBody @Validated TaskEditDto task) {

        TaskReadDto updatedTask = taskService.updateTask(id, task);

        return TaskResponse.builder()
                .id(updatedTask.getId())
                .header(updatedTask.getHeader())
                .body(updatedTask.getBody())
                .status(updatedTask.getStatus().name())
                .doneAt(updatedTask.getDoneAt())
                .build();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }

}
