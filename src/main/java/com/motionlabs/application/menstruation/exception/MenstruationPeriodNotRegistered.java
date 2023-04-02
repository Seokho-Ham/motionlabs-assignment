package com.motionlabs.application.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class MenstruationPeriodNotRegistered extends MotionlabsRuntimeException {

    public MenstruationPeriodNotRegistered() {
        super("월경 주기가 등록되어 있지 않습니다. 주기를 먼저 등록해주세요.");
    }
}
