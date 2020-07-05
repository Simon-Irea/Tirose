package org.tirose.core.boot.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: myf
 * @Author: maoyf@kamfu.com.cn
 * @Date: 2020/4/29 11:15
 */
@Target({ElementType.FIELD}) //该注解只能添加到字段上
@Retention(RetentionPolicy.RUNTIME) //运行时注解
@Constraint(validatedBy = ByteEnumValidator.class) //标志该注解是一个验证注解，并指定一个验证类
public @interface ByteEnum {
    /**
     * 输入需要枚举的类型
     */
    byte[] value();

    String message() default "请输入指定的值";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
