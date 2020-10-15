package org.tirose.core.cache.aspect;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.tirose.core.cache.annotation.RedisCache;
import org.tirose.core.tool.api.ApiResponse;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;


/**
 * redisAop
 *
 * @author pjy
 * @date 2020-04-21
 */
@Aspect
@Slf4j
public class RedisAspect {
    private static String TYPE = "java.lang.Integer,java.lang.Double," +
            "java.lang.Float,java.lang.Long,java.lang.Short," +
            "java.lang.Byte,ava.lang.Boolean,java.lang.Char," +
            "java.lang.String,int,double,long,short,byte,boolean,char,float";

    private RedisTemplate redisTemplate;

    @Pointcut("@within(org.tirose.core.cache.annotation.RedisCache)")
        public void queryCachePointcut() {
    }

    @Around("queryCachePointcut()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //类路径名
        RedisCache annotation = joinPoint.getTarget().getClass().getAnnotation(RedisCache.class);
        //类名
        String annotationKey = annotation.key();
        int minutes = annotation.expireTime();
        //获取方法名
        String methodName = signature.getMethod().getName();
        String paramValue = getParamValue(joinPoint);
        String key = annotationKey + ":" + methodName + "_" + paramValue;
        if ((methodName.contains("get") && methodName.substring(0, 3).equalsIgnoreCase("get"))) {
            Object data = getObject(minutes, joinPoint, key);
            return ApiResponse.data(data);
        } else if ((methodName.contains("add")  && methodName.substring(0, 3).equalsIgnoreCase("add"))
                || (methodName.contains("insert") && methodName.substring(0, 6).equalsIgnoreCase("insert"))
                || (methodName.contains("update")&& methodName.substring(0, 6).equalsIgnoreCase("update"))) {
            redisTemplate.delete(redisTemplate.keys(annotationKey + "*"));
        }
        return joinPoint.proceed();
    }

    private Object getObject(int minutes, ProceedingJoinPoint joinPoint, String key) throws Throwable {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        Object object = null;
        if (hasKey) {
            // 缓存中获取到数据，直接返回。
            object = operations.get(key);
            return object;
        }
        if (object == null) {
            // 缓存中没有数据，调用原始方法查询数据库
            object = joinPoint.proceed();
            operations.set(key, object, minutes, TimeUnit.MINUTES); // 设置超时时间30分钟
        }
        return object;
    }

    /**
     * 序列化
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate = redisTemplate;
    }

    private static String getParamValue(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        //获取所有的参数
        Object[] args = joinPoint.getArgs();//参数
        for (int k = 0; k < args.length; k++) {
            Object arg = args[k];
            if (arg == null) {
                continue;
            }
            // 获取对象类型
            String typeName = arg.getClass().getTypeName();
            //如果是基本类型
            if (TYPE.contains(typeName)) {
                    sb.append(arg + "_");
            } else {//不是基本类型
                //1.获取所有的属性
                Field[] fields = arg.getClass().getDeclaredFields();
                //遍历每个参数
                for (int i = 0; i < fields.length; i++) {
                    try {
                        //将私有变公有
                        fields[i].setAccessible(true);
                        //获取参数名
                        String name = fields[i].getName();
                        //获取值
                        Object o = fields[i].get(arg);
                        //如果该参数不为null或者不为空
                        if (o != null && !o.equals("")) {
                            sb.append(name + "_" + o + "_");
                        }
                    } catch (Exception e) {
                        log.error("参数拼接错误!",e);
                    }
                }
            }
        }
        return sb.toString();
    }
}
