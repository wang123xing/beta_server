package cn.beta.platform.enums;


import cn.beta.platform.exception.AppException;

import java.util.Arrays;

/**
 * @Description  登录类型枚举
 */
public enum LoginTypeEnum {

    PLATFORM(1, "运营"),
    COMMISSION_AGENT(2, "代理商");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(LoginTypeEnum::getValue).toArray();

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名
     */
    private String name;

    LoginTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public LoginTypeEnum setValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public LoginTypeEnum setName(String name) {
        this.name = name;
        return this;
    }

    public int[] array() {
        return ARRAYS;
    }
    public static LoginTypeEnum getInstanceByCode(Integer queryCode){
        return Arrays.stream(LoginTypeEnum.values()).filter(x->x.value.equals(queryCode)).findFirst().orElseThrow(()->new AppException(ResultEnum.FAIL));
    }
}
