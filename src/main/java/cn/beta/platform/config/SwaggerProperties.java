package cn.beta.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description  Swagger2 自动配置类
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private String basePackage;

}
