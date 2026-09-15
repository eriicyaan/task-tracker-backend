package com.mapper;

import com.dto.TaskReadDto;
import com.entity.Task;
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
                .doneAt(object.getDoneAt())
                .build();
    }
}
