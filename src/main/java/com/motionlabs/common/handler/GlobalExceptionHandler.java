package com.motionlabs.common.handler;

import com.motionlabs.common.exception.MotionlabsRuntimeException;
import com.motionlabs.ui.dto.CommonResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MotionlabsRuntimeException.class)
    public CommonResponseEntity<Void> handleRuntimeExceptions(MotionlabsRuntimeException exception) {
        return CommonResponseEntity.fail(exception);
    }

}
