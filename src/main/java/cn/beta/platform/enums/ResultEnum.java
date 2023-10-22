package cn.beta.platform.enums;

/**
 * @description: 结果枚举值
 */
public enum ResultEnum {
    /**
     * 错误码枚举类
     * <p>
     * 系统级异常，使用 2-001-000-000 段
     */
    SYS_ERROR(2001001000, "服务端发生异常"),
    /**
     * SUCCESS
     */
    SUCCESS(200, "成功"),
    /**
     * Fail rsp status enum.
     */
    FAIL(999, "失败"),
    /**
     * Exception rsp status enum.
     */
    EXCEPTION(500, "无权限访问(操作)"),
    USER_NOT_FOUNT(-600002, "未获取到用户"),
    PARAM_ERROR(-6000044, "参数异常"),
    TOKEN_NOT_FOUNT(-121314, "TOKEN 过期"),
    TOKEN_LOGOUT(121314, "注销登录成功");
    private int code;

    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
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
