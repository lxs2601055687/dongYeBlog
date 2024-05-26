package cn.bitoffer.improve.exception;

import cn.bitoffer.common.model.ResponseEntity;
import cn.bitoffer.common.model.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResponseEntity.fail(ResponseEnum.SHOW_FAIL.setMessage(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResponseEntity.fail();
    }
}
