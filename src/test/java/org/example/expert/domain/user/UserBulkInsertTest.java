package org.example.expert.domain.user;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserQueryDslRepositoryImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserBulkInsertTest {

    @Autowired
    private UserQueryDslRepositoryImpl userQueryDslRepository;

    @Disabled
    @DisplayName("BulkInsertWithJdbcTemplate")
    @Test
    public void dummyInsertWithJdbcTemplate() {
        for (int i = 0; i < 50; i++) {
            List<User> userList = new ArrayList<>();
            for (int j = 0; j < 20000; j++) {
                //50개씩 중복 닉네임 생성
                String email = "test" + i + "@test" + j + ".com";
                String nickname = "testname" + j;
                String password = "123";
                User user = new User(email, password, UserRole.USER, nickname);
                userList.add(user);
            }
            userQueryDslRepository.userBulkInsert(userList);
        }

    }
}
