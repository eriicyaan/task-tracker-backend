package com.dto;


import com.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEditDto {

    @NotNull
    @NotBlank
    private String header;

    @NotNull
    @NotBlank
    private String body;

    @NotNull
    private TaskStatus status;

    private LocalDate doneAt;
}
