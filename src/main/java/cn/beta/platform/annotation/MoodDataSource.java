package cn.beta.platform.annotation;



import cn.beta.platform.enums.DataSourceType;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoodDataSource {
    DataSourceType value() default DataSourceType.WRITE;
}
