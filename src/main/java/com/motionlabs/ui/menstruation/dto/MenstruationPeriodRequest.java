package com.motionlabs.ui.menstruation.dto;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenstruationPeriodRequest {

    private final int avg_menstruation_period;
    private final int avg_menstruation_days;

    public MenstruationPeriod convertToEntity(Member member) {
        return new MenstruationPeriod(avg_menstruation_period, avg_menstruation_days, member);
    }

}
