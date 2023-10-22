package cn.beta.platform.enums;

import java.util.Arrays;

/**
 * @Description  通用状态枚举
 */
public enum CommonAuditEnum {

    APPLY(0, "申请"),
    ALLOW(1, "允许"),
    DISABLE(2, "禁用");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonAuditEnum::getValue).toArray();

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名
     */
    private String name;

    CommonAuditEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public CommonAuditEnum setValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public CommonAuditEnum setName(String name) {
        this.name = name;
        return this;
    }

    public int[] array() {
        return ARRAYS;
    }

}
