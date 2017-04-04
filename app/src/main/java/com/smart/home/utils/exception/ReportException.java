package com.smart.home.utils.exception;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by hesc on 15/8/25.
 * <p>上报的异常类</p>
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ReportException {
    /**
     * 异常编码
     * @return
     */
    int code() default 0;
}
