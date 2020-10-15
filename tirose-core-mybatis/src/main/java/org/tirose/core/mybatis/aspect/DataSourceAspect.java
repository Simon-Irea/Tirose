package org.tirose.core.mybatis.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.tirose.core.mybatis.annotation.TargetDataSource;
import org.tirose.core.mybatis.config.DynamicDataSourceHolder;

import java.lang.reflect.Method;


/**
 * @author pjy
 * @date 2020-05-05
 * 该切面能简化就简化,提升性能
 */
@Aspect
@Component
@Order(-999)
@Log4j2
public class DataSourceAspect implements Ordered {
    //切入点
    @Pointcut("@annotation(org.tirose.core.mybatis.annotation.TargetDataSource)")
    public void dataSourcePointCut() {
    }

    /**
     *  该处选择环绕通知,目的为了清除当前线程的Treathlocal数据,防止内存泄漏
     */
    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        TargetDataSource ds = method.getAnnotation(TargetDataSource.class);
        DynamicDataSourceHolder.setDataSource(ds.name().getKey());
        log.info("数据源切换,当前数据库 {}",ds.name().getKey());
        try {
            return point.proceed();
        } finally {
            //防止TreathLocal内存泄漏
            DynamicDataSourceHolder.clearDataSource();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
