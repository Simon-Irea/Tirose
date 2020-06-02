package org.tirose.core.log.persistence;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.tirose.core.log.annotation.ApiLog;

import java.lang.reflect.Method;

/**
 * 这个接口使用在${ApiLogAspect}中，调用了该接口的saveLog方法
 * @Auther: 毛一凡
 * @Email: 349676014@qq.com
 * @Date: 2020/6/2 15:55:00
 */
public interface LogPersistence {
    /**
     * 这是一个钩子函数，若你不太了解ProceedingJoinPoint的使用方法，方法内提供了一些基本的使用
     * 你需要实现该接口，并重写saveLog方法，以便正常使用日志功能
     * @param joinPoint
     * @param time
     */
    default void saveLog(ProceedingJoinPoint joinPoint, long time){
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        ApiLog apiLog = method.getAnnotation(ApiLog.class);
//        if (apiLog != null) {
//            // 注解上的描述
//            System.out.println(apiLog.value());
//        }
//        // 请求的类名
//        String className = joinPoint.getTarget().getClass().getName();
//        //请求的方法名
//        String methodName = signature.getName();
//
//        // 获取方法请求的参数
//        Object[] args = joinPoint.getArgs();

    };
}
