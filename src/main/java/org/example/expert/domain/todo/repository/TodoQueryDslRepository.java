package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.TodoSearchDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoQueryDslRepository {

    Optional<Todo> findByIdWithUser(Long todoId);
    Page<TodoSearchDto> findByKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword, String managerName, LocalDateTime date, LocalDateTime localDateTime, Pageable pageable);
}
