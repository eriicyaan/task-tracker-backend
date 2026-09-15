package com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String header;

    private String body;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDate doneAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
