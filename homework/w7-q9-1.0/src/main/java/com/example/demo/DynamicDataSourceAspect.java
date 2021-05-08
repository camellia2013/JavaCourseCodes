package com.example.demo;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {
    //拦截DbAnnotation
    @Pointcut("@within(com.example.demo.DbAnnotation)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String dsKey = this.getDSAnnotation(joinPoint).value();
        DynamicDataSourceContextHolder.setContextKey(dsKey);
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            throw ex;
        } finally {
            DynamicDataSourceContextHolder.removeContextKey();
        }
    }

    /**
     * 根据类或方法获取数据源注解
     */
    private DbAnnotation getDSAnnotation(ProceedingJoinPoint joinPoint) {
        //mybatis生成的代理类，所以获取它的接口来获取DbAnnotation注解信息
        Class<?> targetClass = joinPoint.getTarget().getClass().getInterfaces()[0];
        DbAnnotation dsAnnotation = targetClass.getAnnotation(DbAnnotation.class);
        // 先判断类的注解，再判断方法注解
        if (Objects.nonNull(dsAnnotation)) {
            return dsAnnotation;
        } else {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            DbAnnotation annotation = methodSignature.getMethod().getAnnotation(DbAnnotation.class);
            return annotation;
        }
    }
}

