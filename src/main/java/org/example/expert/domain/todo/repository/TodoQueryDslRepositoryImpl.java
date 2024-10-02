package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoQueryDslRepositoryImpl implements TodoQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(queryFactory
                .select(todo)
                .from(todo)
                .join(todo.user)
                .fetchJoin()
                .where(
                        todo.id.eq(todoId)
                ).fetchOne());
    }
}
