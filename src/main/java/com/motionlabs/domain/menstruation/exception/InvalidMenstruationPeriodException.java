package com.motionlabs.domain.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class InvalidMenstruationPeriodException extends MotionlabsRuntimeException {

    public InvalidMenstruationPeriodException() {
        super("올바르지 않은 월경 주기 혹은 기간입니다.");
    }
}
