package cn.bitoffer.seckill.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ResponseEntity<T> implements Serializable {

    private int code;
    private String message;
    private String datetime;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> ResponseEntity<T> ok(T data) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(data);
        responseEntity.setCode(ResponseEnum.OK.code());
        responseEntity.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return responseEntity;
    }

    public static <T> ResponseEntity<T> resp(ResponseEnum re, T data) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setData(data);
        responseEntity.setCode(re.code());
        responseEntity.setMessage(re.message());
        responseEntity.setDatetime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return responseEntity;
    }

    public static <T> ResponseEntity<T> ok() {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(ResponseEnum.OK.code());
        responseEntity.setMessage(ResponseEnum.OK.message());
        return responseEntity;
    }


    public static <T> ResponseEntity<T> fail() {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(ResponseEnum.FAIL.message());
        responseEntity.setCode(ResponseEnum.FAIL.code());
        return responseEntity;
    }

    public static <T> ResponseEntity<T> fail(ResponseEnum responseEnum) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(responseEnum.message());
        responseEntity.setCode(responseEnum.code());
        return responseEntity;
    }

    public static <T> ResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
        ResponseEntity<T> responseEntity = new ResponseEntity<>();
        responseEntity.setMessage(responseEnum.message());
        responseEntity.setCode(responseEnum.code());
        responseEntity.setData(data);
        return responseEntity;
    }

}
