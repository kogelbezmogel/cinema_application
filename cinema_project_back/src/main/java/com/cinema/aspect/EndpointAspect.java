package com.cinema.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class EndpointAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointAspect.class);

    @Before("execution(* com.cinema.controller.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        LOGGER.info("Endpoint invoked: {}", signature.getMethod().getName());
        LOGGER.info("Received {} arguments", joinPoint.getArgs().length);
        Arrays.stream(joinPoint.getArgs()).forEach(o -> LOGGER.info("Arg: {}", o.toString()));
    }

    @AfterReturning(
            value = "execution (* com.cinema.controller.*.*(..))",
            returning = "response"
    )
    public void afterControllerReturns(ResponseEntity<?> response) {
        LOGGER.info("Endpoint returned: " + response.toString());
    }

}
