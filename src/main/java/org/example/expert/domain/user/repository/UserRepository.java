package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.dto.UserResponseMapping;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryDslRepository {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserResponseMapping> findByNickname(String nickname);
}
