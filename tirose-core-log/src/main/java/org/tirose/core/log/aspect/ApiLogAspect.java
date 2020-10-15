package org.tirose.core.log.aspect;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.tirose.core.log.annotation.ApiLog;
import org.tirose.core.log.publisher.ApiLogPublisher;

/**
 * @ApiLog 注解的切面日志类
 * @see @ApiLog
 */
@Aspect
@Component
@Data
public class ApiLogAspect {

    @Pointcut("@annotation(org.tirose.core.log.annotation.ApiLog)")
    public void logPointCut() {
    }

    @Around(value = "logPointCut() && @annotation(apiLog)")
    public Object around(ProceedingJoinPoint point,ApiLog apiLog) throws Throwable {
    	String className = point.getTarget().getClass().getName();
		String methodName = point.getSignature().getName();

		long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
		ApiLogPublisher.publishEvent(methodName, className, apiLog, time);
        return result;
    }
}
