package com.example.reggie_waimai.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */

@Component
public class BaseContext {
    private static ThreadLocal<Long> threadLocal1 =new ThreadLocal<>();
    private static ThreadLocal<Long> threadLocal2 = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal1.set(id);
    }

    public static void setEmployeeId(Long id){
        threadLocal2.set(id);
    }


    /**
     * 获取值
     * @return
     */

    public static Long getCurrentId(){
        return threadLocal1.get();
    }

    public static Long getEmployeeId(){
        return threadLocal2.get();
    }
}