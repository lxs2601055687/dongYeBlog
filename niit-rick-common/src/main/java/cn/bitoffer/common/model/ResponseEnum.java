package cn.bitoffer.common.model;

public enum ResponseEnum {
    OK(0, "ok"),
    FAIL(1, "fail"),
    /**
     * 用于直接显示提示用户的错误，内容由输入内容决定
     */
    SHOW_FAIL(1, ""),
    ;
    private final int code;
    private String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
    // 添加一个方法来设置 SHOW_FAIL 的消息
    public ResponseEnum setMessage(String message) {
        if (this == SHOW_FAIL) {
            this.message = message;
        }
        return this;
    }
}
