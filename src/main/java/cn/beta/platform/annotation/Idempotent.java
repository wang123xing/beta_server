package cn.beta.platform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等的组名
     * @return
     */
    String group();
    /**
     * 幂等的key 使用SPEL表达式来创建#request.id+'-'+#request.code
     * @return
     */
    String express();
}
