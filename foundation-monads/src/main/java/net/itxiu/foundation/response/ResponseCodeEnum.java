package net.itxiu.foundation.response;

public enum ResponseCodeEnum {
    SUCCESS(200, "成功"),
    SERVER_ERROR(500, "系统错误"),
    BUSINESS_ERROR(501, "敬请期待");

    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
