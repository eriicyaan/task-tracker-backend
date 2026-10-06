package com.tasktracker.dto;

import com.tasktracker.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReadDto {
    private UUID id;
    private String header;
    private String body;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant completedAt;
}
