package com.example.demo;


import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DbAnnotation {
    /**
     * 数据源名称，默认master
     */
    String value() default DataSourceConstants.DS_KEY_MASTER;
}

