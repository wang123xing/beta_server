package cn.beta.platform.enums;

import java.util.Arrays;

/**
 * @Description  短剧上下架状态枚举
 */
public enum SeriesStatusEnum {

    UP(1, "上架"),
    DOWN(2, "下架"),
    DELETE(3, "删除");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(SeriesStatusEnum::getValue).toArray();

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名
     */
    private String name;

    SeriesStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public SeriesStatusEnum setValue(Integer value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public SeriesStatusEnum setName(String name) {
        this.name = name;
        return this;
    }

    public int[] array() {
        return ARRAYS;
    }

}
