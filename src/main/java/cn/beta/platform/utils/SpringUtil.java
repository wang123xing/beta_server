package cn.beta.platform.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description  SpringBeanUtil
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static <T> T  getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }


    public  <T> Map<String, T> getBeansOfType(Class<T> clz) {
        return applicationContext.getBeansOfType(clz);
    }

    public static <T> T getBean(String name,Class<T> clz){
        return applicationContext.getBean(name,clz);
    }
}
