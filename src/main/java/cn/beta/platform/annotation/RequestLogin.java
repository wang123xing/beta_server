package cn.beta.platform.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLogin {
    /**
     * 请求次数
     * @return
     */
    String maxCount() default "";
}
