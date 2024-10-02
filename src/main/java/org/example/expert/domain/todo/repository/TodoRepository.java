package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQueryDslRepository {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "JOIN FETCH t.user " +
            "WHERE (:weather is NULL OR t.weather = :weather) " +
            "AND (:startDate is NULL OR t.modifiedAt > :startDate) " +
            "AND (:endDate is NULL  OR t.modifiedAt < :endDate) " +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findByWeatherAndDate(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate,
                                    @Param("weather") String weather,
                                    Pageable pageable);
}
