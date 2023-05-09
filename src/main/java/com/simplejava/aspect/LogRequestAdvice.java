package com.simplejava.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 5/8/2023
 * Time: 10:47 PM
 */
@Aspect
@Component
@Slf4j
public class LogRequestAdvice {

    @Autowired
    ObjectMapper mapper;

    @Pointcut(value = "@annotation(LogRequest)")
    public void requestLogPointcut() {

    }

    @Around("requestLogPointcut()")
    public Object LogHttpRequestAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object respone = joinPoint.proceed();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        log.info(
                "HTTP Method {} and URI {} from adresss {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, List<String>> headersMap = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h))
                ));

        log.info("Header Key and values:"+mapper.writeValueAsString(headersMap));

        log.info("Request parameter {}",
                mapper.writeValueAsString(joinPoint.getArgs()));
        return respone;

    }

}


