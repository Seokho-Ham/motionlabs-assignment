package com.motionlabs.application.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class PeriodAlreadyRegisteredException extends MotionlabsRuntimeException {

    public PeriodAlreadyRegisteredException() {
        super("이미 등록된 월경 주기에 대한 정보가 존재합니다.");
    }
}
