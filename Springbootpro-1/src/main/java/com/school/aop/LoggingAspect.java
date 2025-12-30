package com.school.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

  @Around("execution(* com.school.service..*(..)) || execution(* com.school.web..*(..))")
  public Object logExecution(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.nanoTime();
    MethodSignature sig = (MethodSignature) pjp.getSignature();
    String method = sig.getDeclaringType().getSimpleName() + "." + sig.getName();
    Object[] args = pjp.getArgs();

    log.info("Entering {} with args {}", method, Arrays.toString(args));
    try {
      Object result = pjp.proceed();
      long timeMs = (System.nanoTime() - start) / 1_000_000;
      log.info("Exiting {} in {} ms", method, timeMs);
      return result;
    } catch (Throwable ex) {
      long timeMs = (System.nanoTime() - start) / 1_000_000;
      log.error("Exception in {} after {} ms: {}", method, timeMs, ex.getMessage(), ex);
      throw ex;
    }
  }
}
