package cn.beta.platform.context;

import lombok.Data;

@Data
public class UserSecurityContext {
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 用户token
     */
    private String token;

}
