package com.motionlabs.ui.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponseEntity<T> extends ResponseEntity<CommonResponse<T>> {

    private CommonResponseEntity(CommonResponse<T> body) {
        super(body, HttpStatus.OK);
    }

    private CommonResponseEntity(CommonResponse<T> body, HttpStatus httpStatus) {
        super(body, httpStatus);
    }

    public static CommonResponseEntity<Void> success(ResponseMessages responseMessage) {
        return new CommonResponseEntity<>(
            new CommonResponse<>(HttpStatus.OK.value(), responseMessage.getMessage()));
    }

    public static <T> CommonResponseEntity<T> success(T data, ResponseMessages responseMessage) {
        return new CommonResponseEntity<>(
            new CommonResponse<>(HttpStatus.OK.value(), data, responseMessage.getMessage()));
    }

    public static <T> CommonResponseEntity<T> fail(Throwable exception) {
        return new CommonResponseEntity<>(
            new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
