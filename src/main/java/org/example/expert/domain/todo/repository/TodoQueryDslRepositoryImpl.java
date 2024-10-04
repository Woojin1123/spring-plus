package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.TodoSearchDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

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

    @Override
    public Page<TodoSearchDto> findByKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword, String managerName, LocalDateTime date, LocalDateTime localDateTime, Pageable pageable) {
        List<TodoSearchDto> todos = queryFactory
                .select(
                        Projections.constructor(
                                TodoSearchDto.class,
                                todo.title,
                                comment.count(),
                                manager.count()
                        ))
                .from(todo)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(
                        todoTitleContains(keyword),
                        todoCreatedAtBetween(startDate, endDate),
                        todoManagerNameContains(managerName)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(todos, pageable, todos.size());
    }

    private BooleanExpression todoTitleContains(String keyword) {
        return keyword != null ? todo.title.contains(keyword) : null;
    }

    private BooleanExpression todoCreatedAtGoe(LocalDateTime startDate) {
        return startDate != null ? todo.createdAt.goe(startDate) : null;
    }

    private BooleanExpression todoCreatedAtLoe(LocalDateTime endDate) {
        return endDate != null ? todo.createdAt.loe(endDate) : null;
    }

    private BooleanExpression todoCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate != null ? todoCreatedAtGoe(startDate).and(todoCreatedAtLoe(endDate)) : todoCreatedAtLoe(endDate);
    }

    private BooleanExpression todoManagerNameContains(String managerName) {
        return managerName != null ? manager.user.nickname.contains(managerName) : null;
    }
}
