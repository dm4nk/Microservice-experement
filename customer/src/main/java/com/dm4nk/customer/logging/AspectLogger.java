package com.dm4nk.customer.logging;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class AspectLogger {

    private Level defaultLoggingLevel;

    private Boolean loggingEnabled;

    @Pointcut("execution(* com.dm4nk.customer.service.*.*(..))")
    void service() {
    }

    @Pointcut(value = "@annotation(logAnnotation)", argNames = "logAnnotation")
    void annotation(final Log logAnnotation) {
    }

    @Around(value = "service())", argNames = "joinPoint")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        return genericLogMessage(joinPoint, defaultLoggingLevel);
    }

    @Around(value = "annotation(logAnnotation))", argNames = "joinPoint, logAnnotation")
    private Object logWithAnnotation(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        return genericLogMessage(joinPoint, logAnnotation.level());
    }

    private Object genericLogMessage(ProceedingJoinPoint joinPoint, Level level) throws Throwable {
        if (!loggingEnabled) return joinPoint.proceed();

        String logMsg = constructLoggingMessage(joinPoint);
        logMessageWithLevel(level, "#started {}", logMsg);
        Object proceed = joinPoint.proceed();

        String endMsg = isNull(proceed) ? "" : "with result [" + proceed + "]";

        logMessageWithLevel(level, "#ended {} {}", logMsg, endMsg);
        return proceed;
    }

    private void logMessageWithLevel(Level level, String message, Object... args) {
        switch (level) {
            case INFO -> log.info(message, args);
            case DEBUG -> log.debug(message, args);
            case TRACE -> log.trace(message, args);
        }
    }

    private String constructLoggingMessage(JoinPoint jp) {
        String clazz = jp.getSignature().getDeclaringTypeName();

        String args = Arrays.stream(jp.getArgs())
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));

        Method method = ((MethodSignature) jp.getSignature()).getMethod();

        return clazz + "#" + method.getName() + ":" + args;
    }
}
