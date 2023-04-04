package com.motionlabs.core.application.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class InvalidMenstruationDate extends MotionlabsRuntimeException {

    public InvalidMenstruationDate() {
        super("유효하지 않은 월경 날짜입니다.");
    }
}
