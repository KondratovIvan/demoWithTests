package com.example.demowithtests.util.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class SQLQueryCounterServiceAspect {

    private Integer repositoryMethodCallsCounter = 0;

    @Pointcut("execution(* com.example.demowithtests.repository.EmployeeRepository+.*(..))")
    public void repositoryMethods(){
    }

    @AfterReturning("repositoryMethods()")
    public void countAfter() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTraceElements) {
            if (element.getClassName().contains("com.example.demowithtests.service.EmployeeServiceBean")) {
                repositoryMethodCallsCounter++;
                log.debug("Repository method calls from service is: " + repositoryMethodCallsCounter);
                break;
            }
        }
    }

}
