package com.kazma.securitydemo.enums;

public enum Status {
    SUCCESS("200", "成功"),
    FAILED("500", "失败"),
    USER_UN_EXITS("10001", "用户不存在"),
    AUTH_FAILD("10002", "认证失败"),
    ;


    private String code;
    private String msg;

    Status(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
