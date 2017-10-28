package com.xiaosuokeji.server.security.app.annotation;

import java.lang.annotation.*;

/**
 * 令牌校验注解
 * Created by xuxiaowei on 2017/10/26.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserAuth {

    boolean required() default true;
}
