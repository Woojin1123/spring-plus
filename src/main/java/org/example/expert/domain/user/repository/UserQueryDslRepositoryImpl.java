package org.example.expert.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryDslRepositoryImpl implements UserQueryDslRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void userBulkInsert(List<User> userList) {
        String sql = "insert into users (email, nickname, password, user_role, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql,
                userList,
                userList.size(),
                (PreparedStatement ps, User user) -> {
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getNickname());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getUserRole().name());
                    ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
                    ps.setTimestamp(6, Timestamp.valueOf(java.time.LocalDateTime.now()));
                });
    }

}
