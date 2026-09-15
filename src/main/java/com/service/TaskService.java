package com.service;

import com.dto.TaskReadDto;
import com.dto.UserReadDto;
import com.entity.Task;
import com.mapper.TaskMapper;
import com.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    public List<TaskReadDto> findAllTasks(String username) {
        UserReadDto user = userService.findUserByUsername(username);
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());

        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }
}
