package com.xiaosuokeji.server.annotation;

import java.lang.annotation.*;

/**
 * 保存Url到session中
 * Created by gustinlau on 08/11/2017.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveURL {
}
