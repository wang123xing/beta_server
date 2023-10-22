package cn.beta.platform.enums;
/**
 * @Description  删除状态枚举
 */
public enum DeletedStatusEnum {

    DELETED_NO(0,"未删除"),
    DELETED_YES(1,"删除");

    private int value;

    private String message;

    DeletedStatusEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
