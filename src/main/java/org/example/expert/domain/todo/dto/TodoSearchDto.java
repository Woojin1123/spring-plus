package org.example.expert.domain.todo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoSearchDto {
    private final String title;
    private final Long commentCount;
    private final Long managerCount;
}
