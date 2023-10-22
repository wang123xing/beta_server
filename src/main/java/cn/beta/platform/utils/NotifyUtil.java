package cn.beta.platform.utils;

import cn.beta.platform.notify.DingDingNotifyMessageText;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: 报警工具包，目前支持钉钉通知
 * @version: 1.0
 */
@Component
public class NotifyUtil {
    private final static Logger logger = LoggerFactory.getLogger(NotifyUtil.class);
    @Value("${webhookUrl.monitor_dingding_broadcast}")
    private String DEFAULT_PATH;
    @Value("${webhookUrl.phones}")
    private String phone;
    private final static HttpClientUtil httpClientUtil=HttpClientUtil.getInstance();

    /**
     *
     * @param path 钉钉webhook路径
     * @param json 发送的消息
     */
    public void sendDingDingMessage(String path,String json){
        httpClientUtil.httpPostMethod(path, json,"application/json" , Maps.newHashMap(), Maps.newHashMap(),"UTF-8");
    }

    /**
     *
     * @param json 发送的消息
     */
    public  void sendDingDingMessage(String json){
       sendDingDingMessage(DEFAULT_PATH,json);
    }
    /**
     * @param throwable 异常信息
     */
    public  void sendDingDingMessage(Throwable throwable){
        sendDingDingMessage(DEFAULT_PATH, DingDingNotifyMessageText.createTextMessage(throwable.getMessage(),phone));
    }
    /**
     * @param exception 异常信息
     */
    public  void sendDingDingMessage(Exception exception){
        sendDingDingMessage(DEFAULT_PATH, DingDingNotifyMessageText.createTextMessage(exception.getMessage(),phone));
    }
}
