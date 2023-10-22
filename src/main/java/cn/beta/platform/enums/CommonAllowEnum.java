package cn.beta.platform.enums;

import java.util.Arrays;

/**
 * @Description  通用状态枚举
 */
public enum CommonAllowEnum {

    ENABLE(0, "允许"),
    DISABLE(1, "禁用");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonAllowEnum::getValue).toArray();

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名
     */
    private String name;

    CommonAllowEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public CommonAllowEnum setValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public CommonAllowEnum setName(String name) {
        this.name = name;
        return this;
    }

    @Deprecated
    public static boolean isValid(Integer status) {
        if (status == null) {
            return false;
        }
        return ENABLE.value.equals(status)
                || DISABLE.value.equals(status);
    }

    public int[] array() {
        return ARRAYS;
    }

}
