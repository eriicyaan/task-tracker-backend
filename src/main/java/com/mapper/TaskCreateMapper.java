package com.mapper;

import com.dto.TaskCreateDto;
import com.entity.Task;
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
