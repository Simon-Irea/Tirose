package org.tirose.core.cache.annotation;


import java.lang.annotation.*;

/**
 * @Description: redis缓存注解 编写在需要缓存的类上
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * @return 缓存的key值
     * 对应的Method的返回值必须 实现 Serializable 接口
     *
     */
    String key();

    /**
     * 到分钟
     *
     */
    int expireTime() default 20;
}
