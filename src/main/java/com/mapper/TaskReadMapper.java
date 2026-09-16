package com.mapper;

import com.dto.TaskReadDto;
import com.dto.response.TaskResponse;
import org.springframework.stereotype.Component;


@Component
public class TaskReadMapper implements Mapper<TaskReadDto, TaskResponse> {
    @Override
    public TaskResponse map(TaskReadDto object) {
        return TaskResponse.builder()
                .id(object.getId())
                .header(object.getHeader())
                .body(object.getBody())
                .status(object.getStatus().name())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt())
                .completedAt(object.getCompletedAt())
                .build();
    }
}
