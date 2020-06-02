package org.tirose.core.log.aspect;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.tirose.core.log.persistence.LogPersistence;

@Aspect
@Component
@Data
public class ApiLogAspect {

	private LogPersistence logPersistence;


    @Pointcut("@annotation(org.tirose.core.log.annotation.ApiLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        logPersistence.saveLog(point, time);
        return result;
    }
}
