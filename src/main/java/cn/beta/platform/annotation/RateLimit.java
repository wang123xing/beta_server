package cn.beta.platform.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RateLimit {
    /**
     * 限流请求
     * @return
     */
    String url();
    /**
     * 限流关键字SPEL表达式 获取参数中的值
     * @return
     */
    String key();
    /**
     * 访问的次数
     * @return
     */
    String limit() default "10";
    /**
     * 请求备注
     * @return
     */
    String info();
}
