package cn.bitoffer.improve.common;

import cn.bitoffer.improve.exception.ErrorCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data

public class ResponseDTO<T> {

    public static final int OK_CODE = 0;

    public static final String OK_MSG = "操作成功";


    private Integer code;


    private String level;

    private String msg;

    private Boolean ok;


    private T data;



    public ResponseDTO(Integer code, String level, boolean ok, String msg, T data) {
        this.code = code;
        this.level = level;
        this.ok = ok;
        this.msg = msg;
        this.data = data;
    }

    public ResponseDTO(Integer code, String level, boolean ok, String msg) {
        this.code = code;
        this.level = level;
        this.ok = ok;
        this.msg = msg;

    }

    public ResponseDTO(ErrorCode errorCode, boolean ok, String msg, T data) {
        this.code = errorCode.getCode();
        this.level = errorCode.getMessage();
        this.ok = ok;
        if (StringUtils.isNotBlank(msg)) {
            this.msg = msg;
        } else {
            this.msg = errorCode.getMessage();
        }
        this.data = data;
    }

    public static <T> ResponseDTO<T> ok() {
        return new ResponseDTO<>(OK_CODE, null, true, OK_MSG, null);
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(OK_CODE, null, true, OK_MSG, data);
    }

    public static <T> ResponseDTO<T> okMsg(String msg) {
        return new ResponseDTO<>(OK_CODE, null, true, msg, null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode) {
        return new ResponseDTO<>(errorCode, false, null, null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode, boolean ok) {
        return new ResponseDTO<>(errorCode, ok, null, null);
    }

    public static <T>  ResponseDTO<T> error(ResponseDTO<?> responseDTO) {
        return new ResponseDTO<>(responseDTO.getCode(), responseDTO.getLevel(), responseDTO.getOk(), responseDTO.getMsg(), null);
    }

    public static <T> ResponseDTO<T> error(ErrorCode errorCode, String msg) {
        return new ResponseDTO<>(errorCode, false, msg, null);
    }

    public static <T> ResponseDTO<T> errorData(ErrorCode errorCode, T data) {
        return new ResponseDTO<>(errorCode, false, null, data);
    }


}
