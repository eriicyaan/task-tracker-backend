package com.dto.response;

import com.entity.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(String header,
                           String body,
                           String status,
                           LocalDate doneAt) {
}
