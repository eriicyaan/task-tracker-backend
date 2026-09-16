package com.dto;


import com.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEditDto {

    @NotNull
    @NotBlank
    @Length(max = 50)
    private String header;

    @NotNull
    @NotBlank
    @Length(max = 255)
    private String body;

    @NotNull
    private TaskStatus status;
}
