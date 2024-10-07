package org.example.expert.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.ExecutionStatus;
import org.example.expert.domain.log.service.ManagerLogService;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j(topic = "Manger Registration AOP")
@Aspect
@Component
@RequiredArgsConstructor
public class ManagerLoggingAspect {
    private final ManagerLogService managerLogService;
    @Pointcut("@annotation(org.example.expert.domain.common.annotation.ManagerRegistrationLog)")
    private void managerLogPointCut() {

    }

    @Around("managerLogPointCut()")
    public Object managerRegistrationLog(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();
        Log managerLog = new Log(startTime);
        log.info("Manager Registration Request At {} ", startTime);
        Object[] args = joinPoint.getArgs();

        if (args[1] instanceof Long) {
            managerLog.setTodoId((Long) args[1]);
            log.info("Manager Registration TodoId : {}", args[1]);
        }
        if (args[2] instanceof ManagerSaveRequest) {
            managerLog.setManagerId(((ManagerSaveRequest) args[2]).getManagerUserId());
            log.info("Manager Registration ManagerId : {}", ((ManagerSaveRequest) args[2]).getManagerUserId());
        }
        try {
            Object result = joinPoint.proceed();
            //성공
            LocalDateTime successTime = LocalDateTime.now();
            managerLog.setCompletedAt(successTime);
            managerLog.setExecutionTime(
                    successTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            );
            managerLog.setStatus(ExecutionStatus.SUCCESS);

            log.info("Manager Registration Success At {} ", successTime);
            log.info("Method Execution Time : {} ms", managerLog.getExecutionTime());

            managerLogService.saveMangerLog(managerLog);

            return result;
        } catch (InvalidRequestException e) {
            //실패
            LocalDateTime failedTime = LocalDateTime.now();
            managerLog.setCompletedAt(failedTime);
            managerLog.setExecutionTime(
                    failedTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            );
            managerLog.setStatus(ExecutionStatus.FAILED);

            log.info("Manager Registration Failed At {} ", failedTime);
            log.info("Method Execution Time : {} ms", managerLog.getExecutionTime());

            managerLogService.saveMangerLog(managerLog);

            throw e;
        }
    }
}
