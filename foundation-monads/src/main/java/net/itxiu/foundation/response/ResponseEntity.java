package net.itxiu.foundation.response;

public class ResponseEntity<T> {
    private Integer code = ResponseCodeEnum.SUCCESS.getCode();
    private String message = ResponseCodeEnum.SUCCESS.getMessage();

    private T data;

    private Throwable reason;

    public ResponseEntity(){}

    public ResponseEntity(Integer code,String message,T data,Throwable reason){
        this.code = code;
        this.message = message;
        this.data = data;
        this.reason = reason;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return code != null && code.equals(ResponseCodeEnum.SUCCESS.getCode());
    }

    public boolean failure(){
        return !success();
    }

    public Throwable getReason() {
        return reason;
    }

    public void setReason(Throwable reason) {
        this.reason = reason;
    }

    public static <B> ResponseEntity<B> success(B data){
        return new ResponseEntity<>(
                ResponseCodeEnum.SUCCESS.getCode(),
                ResponseCodeEnum.SUCCESS.getMessage(),
                data,
                null
        );
    }

    public static <B> ResponseEntity<B> failureWithCustom(Integer code, String message, Throwable reason){
        return new ResponseEntity<>(
                code,
                message,
                null,
                reason
        );
    }

    public static <B> ResponseEntity<B> failure(Throwable reason){
        return new ResponseEntity<>(
                ResponseCodeEnum.SERVER_ERROR.getCode(),
                ResponseCodeEnum.SERVER_ERROR.getMessage(),
                null,
                reason

        );
    }
}
