package cn.beta.platform.entity.dto;

import cn.beta.platform.enums.ResultEnum;

public class ResultBean<T> extends Response implements java.io.Serializable {

    private T data;

    public ResultBean() {
    }

    public ResultBean(T t) {
        this.data = t;
    }

    public static <T> ResultBean<T> success(T t) {
        ResultBean response = new ResultBean<T>();
        response.setData(t);
        return response;
    }

    public static <T> ResultBean<T> success(T t, String message) {
        ResultBean response = new ResultBean<T>();
        response.setData(t);
        response.setMessage(message);
        return response;
    }

    public static <T> ResultBean<T> error(ResultEnum ResultEnum, T t) {
        ResultBean response = new ResultBean<T>();
        response.setStatus(ResultEnum.getCode());
        response.setData(t);
        response.setMessage(ResultEnum.getMessage());
        return response;
    }

    public static <T> ResultBean<T> error(ResultEnum ResultEnum) {
        ResultBean response = new ResultBean<T>();
        response.setStatus(ResultEnum.getCode());
        response.setData(null);
        response.setMessage(ResultEnum.getMessage());
        return response;
    }
    public static <T> ResultBean<T> error(ResultEnum ResultEnum, String message) {
        ResultBean response = new ResultBean<T>();
        response.setStatus(ResultEnum.getCode());
        response.setData(null);
        response.setMessage(message);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
