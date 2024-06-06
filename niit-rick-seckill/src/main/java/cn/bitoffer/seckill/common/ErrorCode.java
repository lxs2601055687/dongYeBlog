package cn.bitoffer.seckill.common;

/**
 * 自定义错误码
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    SECKILL_ERROR(60000,"活动报名失败")
    ;


    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private  String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
