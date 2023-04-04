package com.motionlabs.application.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class InvalidMenstruationDateException extends MotionlabsRuntimeException {

    public InvalidMenstruationDateException() {
        super("유효하지 않은 월경 날짜입니다.");
    }
}
