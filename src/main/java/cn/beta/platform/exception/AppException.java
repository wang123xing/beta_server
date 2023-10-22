package cn.beta.platform.exception;


import cn.beta.platform.enums.ResultEnum;

/**
 * @Description  自定义异常
 */
public class AppException extends RuntimeException{

    private Integer code;

    private ResultEnum resultEnum;

    public AppException() { }

    public AppException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.resultEnum = resultEnum;
    }

    public AppException(ResultEnum resultEnum, String message) {
        this.code = resultEnum.getCode();
        this.resultEnum = resultEnum;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }
}

