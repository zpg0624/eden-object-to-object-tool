package com.eden.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @created by eden
 * @since 2019-06-23 14:38:48
 */

@Target({TYPE, FIELD, METHOD})
@Retention(RUNTIME)
public @interface TransferLabel {
    String value() default "";

    boolean isObject() default false;
}
