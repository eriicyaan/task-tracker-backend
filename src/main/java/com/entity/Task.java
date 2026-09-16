package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Task extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String header;

    private String body;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Instant completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
