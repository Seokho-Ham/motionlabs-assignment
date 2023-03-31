package com.motionlabs.common.exception;

public class NeedAuthorizationException extends MotionlabsRuntimeException {

    public NeedAuthorizationException() {
        super("유저의 로그인 정보가 없습니다.");
    }
}
