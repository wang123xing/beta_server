package cn.beta.platform.enums;


import cn.beta.platform.exception.AppException;

import java.util.Arrays;

/**
 * @author: by Mood
 * @date: 2011-01-14 11:11:11
 * @Description: 订单状态枚举类10-未支付，20-支付中，30-支付成功，40-已取消，50-已完成，60-已发货，
 * 70-交易关闭，80-退款审核中，90-退款审核通过
 * @version: 1.0
 */

public enum OrderStatusEnum {

    UNPAID(10, "unpaid","未支付"),

    PAID_ING(20, "paid_ing","支付中"),

    PAID(30,"paid", "支付成功"),

    CANCEL(40, "cancel","交易取消"),

    FINISH(50, "finish","已发货"),

    DELIVERY_POINT_FINISH(55, "delivery_point_finish","货物已到取货点"),

    SHIPPED(60, "shipped","确认收货"),

    AUTO_FINISH(70, "auto_finish","自动确认收货"),

    REFOUND_APPLY(80, "refound_apply","退款审核中"),

    NOT_SHIPPED_REFOUND_APPLY(81, "not_shipped_refound_apply","未发货取消订单，退款申请"),

    REFOUND_FINISH(90, "refound_finish","退款审核通过"),

    REFOUND_REJECT(100, "refound_reject","退款审核驳回"),

    ORDER_COMPLETE(110, "order_complete","交易完成"),

    ORDER_CLOSED(120, "order_closed","交易关闭"),

    ;

    private int value;

    private String code;

    private String message;

    OrderStatusEnum(int value,String code, String message) {
        this.value = value;
        this.code = code;
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

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets enum.
     *
     * @return the code
     */
    public static OrderStatusEnum getEnumByValue(int param) {
        return Arrays.stream(OrderStatusEnum.values()).filter(orderStatusEnum -> orderStatusEnum.getValue()==param).findFirst().orElseThrow(()->new AppException(ResultEnum.PARAM_ERROR));
    }
}
