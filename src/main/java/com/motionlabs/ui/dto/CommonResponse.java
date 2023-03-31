package com.motionlabs.ui.dto;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

    private final int code;
    private final T data;
    private final String message;

    public CommonResponse(int code, String message) {
        this(code, null, message);
    }

    public CommonResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
