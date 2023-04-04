package com.motionlabs.core.domain.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class InvalidMenstruationHistory extends MotionlabsRuntimeException {

    public InvalidMenstruationHistory() {
        super("올바르지 않은 기록 정보입니다.");
    }
}
