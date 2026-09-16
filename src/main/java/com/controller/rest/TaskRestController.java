package com.controller.rest;


import com.dto.TaskCreateDto;
import com.dto.TaskEditDto;
import com.dto.TaskReadDto;
import com.dto.response.TaskResponse;
import com.mapper.TaskReadMapper;
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
    private final TaskReadMapper taskReadMapper;

    @GetMapping
    public List<TaskResponse> getTasks(Authentication authentication) {
        List<TaskReadDto> tasks = taskService.findAllTasks(authentication.getName());


        return tasks.stream()
                .map(taskReadMapper::map)
                .toList();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@PathVariable UUID id) {
        TaskReadDto task = taskService.findTaskById(id);

        return taskReadMapper.map(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody @Validated TaskCreateDto task,
                                   Authentication authentication) {
        TaskReadDto createdTask = taskService.createTask(task, authentication.getName());

        return taskReadMapper.map(createdTask);

    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse updateTask(@PathVariable UUID id,
                                   @RequestBody @Validated TaskEditDto task) {

        TaskReadDto updatedTask = taskService.updateTask(id, task);

        return taskReadMapper.map(updatedTask);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }


    @PostMapping("/{id}")
    public TaskResponse completeTask(@PathVariable UUID id) {
        TaskReadDto completedTask = taskService.completeTask(id);

        return taskReadMapper.map(completedTask);
    }
}
