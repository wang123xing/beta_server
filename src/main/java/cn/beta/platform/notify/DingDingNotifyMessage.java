package cn.beta.platform.notify;

import lombok.Data;

@Data
public class DingDingNotifyMessage {
    private String msgtype;
    private DingDingNotifyAT at;
}
