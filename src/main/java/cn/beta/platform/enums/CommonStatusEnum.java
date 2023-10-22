package cn.beta.platform.enums;

import java.util.Arrays;

/**
 * @Description  通用状态枚举
 */
public enum CommonStatusEnum  {

    ENABLE(1, "开启"),
    DISABLE(2, "关闭");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonStatusEnum::getValue).toArray();

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名
     */
    private String name;

    CommonStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public CommonStatusEnum setValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public CommonStatusEnum setName(String name) {
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
