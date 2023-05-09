package com.simplejava.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 5/8/2023
 * Time: 11:32 PM
 */
@Aspect
@Component
@Slf4j
public class LogResponseAdvice {
    @Autowired
    private ObjectMapper mapper;

    @PostConstruct
    public void init(){
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    @Pointcut(value = "@annotation(LogResponse)")
    public void responseLogPointcut() {

    }

    @Around("responseLogPointcut()")
    public Object LogHttpResponseAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
        Object responePayLoad = joinPoint.proceed();
        Object responseObject;
        if (responePayLoad instanceof ResponseEntity<?>) {
            ResponseEntity re = (ResponseEntity) responePayLoad;
            responseObject = re.getBody();
            if (null == responseObject) {
                return null;
            }
            log.info("Reponse Status Code::{} Headers::{} Body:: {} is:",re.getStatusCode(),
                    re.getHeaders(),mapper.writeValueAsString(responseObject));

        }
        return responePayLoad;
    }
}
