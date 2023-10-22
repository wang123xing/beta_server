package cn.beta.platform.config;

import lombok.Data;

/**
 * @Description: 幂等base请求类
 * @version: 1.0
 */
@Data
public class BaseRequestParam {
    private String uuid;
    private String timeStamp;
}
