package com.motionlabs.ui.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessages {

    REGISTER_MENSTRUATION_PERIOD_SUCCESS("월경 주기 등록에 성공했습니다.");

    private final String message;

}