package com.service;

import com.dto.*;

import com.entity.Task;
import com.entity.TaskField;
import com.entity.TaskStatus;
import com.entity.User;
import com.exception.FieldNotValidException;
import com.exception.TaskNotExistsException;
import com.mapper.TaskCreateMapper;
import com.mapper.TaskMapper;
import com.repository.TaskRepository;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final TaskCreateMapper taskCreateMapper;

    public List<TaskReadDto> findAllTasks(String username) {
        UserReadDto user = userService.findUserByUsername(username);
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());

        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskReadDto findTaskById(UUID id) {
        Optional<Task> task = taskRepository.findById(id);

        if(task.isEmpty()) {
            throw new TaskNotExistsException("task not exists");
        }

        return task
                .map(taskMapper::map)
                .get();
    }

    public TaskReadDto createTask(TaskCreateDto task, String username) {
        User user = userRepository.findUserByUsername(username);

        Task createdTask = taskCreateMapper.map(task);

        createdTask.setStatus(TaskStatus.TODO);
        createdTask.setUser(user);

        Task savedTask = taskRepository.save(createdTask);

        return taskMapper.map(savedTask);

    }

    public TaskReadDto updateTask(UUID id, TaskEditDto newTask) {
        Task task = findTask(id);

        task.setHeader(newTask.getHeader());
        task.setBody(newTask.getBody());
        task.setStatus(newTask.getStatus());


        return taskMapper.map(task);
    }

    public void deleteTask(UUID id) {
        findTask(id);

        taskRepository.deleteById(id);
    }

    public TaskReadDto completeTask(UUID id) {
        Task task = findTask(id);

        task.setStatus(TaskStatus.COMPLETED);
        task.setCompletedAt(Instant.now());

        return taskMapper.map(task);
    }

    public void updateTaskField(UUID id,
                                TaskField field,
                                TaskFieldUpdateDto taskFieldUpdateDto) {
        Task task = findTask(id);

        switch (field) {
            case HEADER -> updateHeader(task, taskFieldUpdateDto.value());
            case BODY -> updateBody(task, taskFieldUpdateDto.value());
            case STATUS -> updateStatus(task, taskFieldUpdateDto.value());
        }

    }


    private void updateHeader(Task task, String value) {
        if(value.length() > 50) {
            throw new FieldNotValidException("the value length is too long");
        }
        task.setHeader(value);
    }

    private void updateBody(Task task, String value) {
        if(value.length() > 255) {
            throw new FieldNotValidException("the value length is too long");
        }
        task.setBody(value);
    }

    private void updateStatus(Task task, String value) {
        try{
            TaskStatus taskStatus = TaskStatus.valueOf(value.toUpperCase());
            task.setStatus(taskStatus);
        } catch (IllegalArgumentException e) {
            throw new FieldNotValidException("the value is not valid");
        }
    }

    private Task findTask(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotExistsException("task not exists"));
    }
}
