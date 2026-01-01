package com.school.edureka_student.aop;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.school.edureka_student..*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getDeclaringType().getSimpleName()
                + "." + methodSignature.getName();

        Object[] args = joinPoint.getArgs();

        System.out.println("➡ Method Started: " + methodName);
        System.out.println("➡ Arguments: " + Arrays.toString(args));

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("⬅ Method Completed: " + methodName);
        System.out.println("⏱ Execution Time: " + executionTime + " ms");
        System.out.println("------------------------------------------------");

        return result;
    }
}