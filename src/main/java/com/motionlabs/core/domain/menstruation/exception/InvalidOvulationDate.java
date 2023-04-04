package com.motionlabs.core.domain.menstruation.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class InvalidOvulationDate extends MotionlabsRuntimeException {

    public InvalidOvulationDate() {
        super("배란기 종료일이 시작일보다 이전일 수 없습니다.");
    }
}
