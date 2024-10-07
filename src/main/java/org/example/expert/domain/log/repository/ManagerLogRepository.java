package org.example.expert.domain.log.repository;

import org.example.expert.domain.log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerLogRepository extends JpaRepository<Log, Long> {
}
