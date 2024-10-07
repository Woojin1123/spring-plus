package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.expert.domain.log.enums.ExecutionStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long todoId;
    private Long managerId;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private Long executionTime;
    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    public Log(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
