package com.service;

import com.dto.TaskCreateDto;

import com.dto.TaskEditDto;
import com.dto.TaskReadDto;
import com.dto.UserReadDto;
import com.entity.Task;
import com.entity.TaskStatus;
import com.entity.User;
import com.exception.TaskNotExistsException;
import com.mapper.TaskCreateMapper;
import com.mapper.TaskMapper;
import com.repository.TaskRepository;
import com.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

        createdTask.setStatus(TaskStatus.CREATED);
        createdTask.setUser(user);

        Task savedTask = taskRepository.save(createdTask);

        return TaskReadDto.builder()
                .id(savedTask.getId())
                .header(savedTask.getHeader())
                .body(savedTask.getBody())
                .status(savedTask.getStatus())
                .doneAt(savedTask.getDoneAt())
                .build();

    }

    public TaskReadDto updateTask(UUID id, TaskEditDto newTask) {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotExistsException("task not exists"));

        task.setHeader(newTask.getHeader());
        task.setBody(newTask.getBody());
        task.setStatus(newTask.getStatus());
        task.setDoneAt(newTask.getDoneAt());


        return taskMapper.map(task);
    }

    public void deleteTask(UUID id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotExistsException("task not exists"));

        taskRepository.deleteById(id);
    }
}
