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
public class UserCreateEditDto {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 6, max = 30)
    private String password;
}
