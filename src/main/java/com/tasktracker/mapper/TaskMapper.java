package com.tasktracker.mapper;

import com.tasktracker.dto.TaskReadDto;
import com.tasktracker.entity.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper implements Mapper<Task, TaskReadDto> {

    @Override
    public TaskReadDto map(Task object) {
        return TaskReadDto.builder()
                .id(object.getId())
                .header(object.getHeader())
                .body(object.getBody())
                .status(object.getStatus())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt())
                .completedAt(object.getCompletedAt())
                .build();
    }
}
