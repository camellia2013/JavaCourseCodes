package com.example.demo;


import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DynamicDataSourceContextHolder {
    /* 动态数据源名称上下文*/
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    /* 设置/切换数据源*/
    public static void setContextKey(String key) {
        System.out.println("切换数据源"+key);
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    /* 获取数据源名称 */
    public static String getContextKey() {
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        return key == null ? DataSourceConstants.DS_KEY_MASTER : key;
    }

    /*删除当前数据源名称*/
    public static void removeContextKey() {
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}
