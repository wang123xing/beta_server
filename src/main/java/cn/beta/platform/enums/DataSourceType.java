package cn.beta.platform.enums;
/**
 * @Description: 数据源枚举类
 * @version: 1.0
 */

public enum DataSourceType {
    /**
     * 写数据源
     */
    WRITE("write"),
    /**
     * 读数据源
     */
    READ("read");
    public String value;
    DataSourceType(String value){
        this.value=value;
    }
    public String getValue() {
        return this.value;
    }
    public static String getValue(String value) {
        for (DataSourceType o : DataSourceType.values()) {
            if (o.getValue().equals(value)) {
                return o.getValue();
            }
        }
        return "";
    }
}
