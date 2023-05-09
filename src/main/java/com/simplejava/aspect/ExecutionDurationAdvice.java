package com.simplejava.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 5/8/2023
 * Time: 10:34 PM
 */
@Aspect
@Component
@Slf4j
public class ExecutionDurationAdvice {

    @Pointcut(value = "@annotation(LogExecutionTime)")
    public void durationTimePointcut() {

    }

    @Around("durationTimePointcut()")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime=System.currentTimeMillis();
        Object obj=joinPoint.proceed();
        long endTime=System.currentTimeMillis();
        log.info("Method name"+joinPoint.getSignature()+" time taken to execute in milli seconds : "+(endTime-startTime));
        return obj;
    }
}
