package org.tirose.core.boot.validate;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: myf
 * @Author: maoyf@kamfu.com.cn
 * @Date: 2020/4/29 11:21
 */
@Slf4j
public class ByteEnumValidator implements ConstraintValidator<ByteEnum,Object> {
    private final static String METHOD_NAME = "getValue";
    private ByteEnum annotation;

    @Override
    public void initialize(ByteEnum constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        byte[] bytes = annotation.value();
        if (bytes.length == 0 ) {
            //为true是不通过
            return false;
        }
        boolean result = false;
        try {
            for (int i = 0; i < bytes.length; i++) {

                if (((Byte)value).equals(bytes[i])) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("EnumValidator call isValid() method exception", e);
        }
        return result;
    }
}
