package com.dto;

import com.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReadDto {
    private String header;
    private String body;
    private TaskStatus status;
    private LocalDate doneAt;
}
