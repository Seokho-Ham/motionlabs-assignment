package com.motionlabs.core.application.member.exception;

import com.motionlabs.common.exception.MotionlabsRuntimeException;

public class MemberNotFoundException extends MotionlabsRuntimeException {

    public MemberNotFoundException() {
        super("해당 회원을 찾을 수 없습니다.");
    }
}
