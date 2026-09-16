package com.dto.response;

import com.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
public record TaskResponse(UUID id,
                           String header,
                           String body,
                           String status,
                           Instant createdAt,
                           Instant updatedAt,
                           @JsonInclude(NON_NULL) Instant completedAt) {
}
