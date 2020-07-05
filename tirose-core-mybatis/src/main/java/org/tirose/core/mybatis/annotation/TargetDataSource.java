package org.tirose.core.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author pjy
 * @date 2020-05-05
 * 注意:该注解在mapper层和service层实现类的方法都可用,
 * 根据开发需求定制,
 * 1. 如果该service都是查询功能,建议在service上注解
 * 2. 如果有CRUD操作则选择在mapper层注解
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    com.kamfu.supporttong.common.config.mybatisconfig.DataSourceKey name() default com.kamfu.supporttong.common.config.mybatisconfig.DataSourceKey.DB_FIRST;
}
