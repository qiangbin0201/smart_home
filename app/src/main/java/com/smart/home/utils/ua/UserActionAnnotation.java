package com.smart.home.utils.ua;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
@interface UserActionAnnotation {
	abstract boolean needUpload() default true;
}
