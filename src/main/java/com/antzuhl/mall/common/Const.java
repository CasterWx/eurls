package com.antzuhl.mall.common;

public class Const {
    public static final String CURRENT_USER = "currentUser";
    public static final Integer ROLE_CUSTOMER = 0;
    public static final Integer ROLE_ADMIN = 1;


    public enum OrderStatusEnum {
        CANCELED(0,"已取消"),
        NO_PAY(10, "未支付"),
        PAID(20, "已支付"),
        SHIPPED(40, "已发货"),
        ORDER_SUCCESS(50, "订单完成"),
        ORDER_CLOSE(60, "订单关闭");
        private String value;
        private int code;
        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }
        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }

    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }


    public enum PayPlatformEnum {
        ALIPAY(1, "支付宝");
        private String value;
        private int code;
        PayPlatformEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }
        public int getCode() {
            return code;
        }
        public String getValue() {
            return value;
        }
    }

}
