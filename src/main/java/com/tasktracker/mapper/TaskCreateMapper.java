package com.tasktracker.mapper;

import com.tasktracker.dto.TaskCreateDto;
import com.tasktracker.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateMapper implements Mapper<TaskCreateDto, Task> {

    @Override
    public Task map(TaskCreateDto object) {
        return Task.builder()
                .header(object.getHeader())
                .body(object.getBody())
                .build();
    }
}
