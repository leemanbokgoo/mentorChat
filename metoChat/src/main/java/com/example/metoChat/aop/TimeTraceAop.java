package com.example.metoChat.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
public class TimeTraceAop {

    @Around("execution(* com.example.metoChat..*(..)) && !target(com.example.metoChat.config.SpringConfig)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();

        /// 나중에 로그로 변경
        System.out.println("START : " + joinPoint.toShortString());
        try {
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("END : " + joinPoint.toShortString() + "  " + timeMs + "ms");
        }

    }
}
