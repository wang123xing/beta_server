package cn.beta.platform.aop;

import cn.beta.platform.entity.dto.ResultBean;
import cn.beta.platform.enums.ResultEnum;
import cn.beta.platform.exception.AppException;
import cn.beta.platform.utils.NotifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@ControllerAdvice(basePackages = "cn.beta.platform")
public class GlobalExceptionHandler {
    @Autowired
    NotifyUtil notifyUtil;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBean exceptionHandler(Exception e) {
        log.error("【系统抛出Exception异常】 —— 异常内容如下：{}", e.getMessage(), e);
        ResultBean Result = new ResultBean<>();
        Result.setStatus(ResultEnum.FAIL.getCode());
        Result.setMessage(ResultEnum.FAIL.getMessage());
//        dingDingNotifyUtil.sendDingDingMessage(e);
        return Result;
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResultBean defaultException(AppException e) {
        log.error("【系统抛出AppException异常】 —— 异常内容如下：{}", e.getMessage(), e);
        ResultBean Result = new ResultBean<>();
        Result.setStatus(e.getResultEnum().getCode());
        Result.setMessage(e.getResultEnum().getMessage());
//        dingDingNotifyUtil.sendDingDingMessage(e);
        return Result;
    }
}
