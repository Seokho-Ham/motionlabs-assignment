package com.motionlabs.integration.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class DuplicatedMenstruationHistoryException extends MotionlabsRuntimeException {

    public DuplicatedMenstruationHistoryException() {
        super("해당 기간에 대한 월경기록이 이미 존재합니다.");
    }
}
