package com.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskCreateDto {

    @NotNull
    @NotBlank
    @Length(max = 50)
    private String header;

    @NotNull
    @NotBlank
    @Length(max = 255)
    private String body;

}
