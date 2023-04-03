package com.motionlabs.application.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class MenstruationHistoryNotFound extends MotionlabsRuntimeException {

    public MenstruationHistoryNotFound() {
        super("해당하는 날짜의 월경 기록을 찾을 수 없습니다.");
    }
}
