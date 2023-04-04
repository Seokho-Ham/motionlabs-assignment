package com.motionlabs.ui.menstruation.dto;

import com.motionlabs.core.domain.member.Member;
import com.motionlabs.core.domain.menstruation.MenstruationPeriod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenstruationPeriodRequest {

    private final int avgMenstruationPeriod;
    private final int avgMenstruationDays;

    public MenstruationPeriod convertToEntity(Member member) {
        return new MenstruationPeriod(avgMenstruationPeriod, avgMenstruationDays, member);
    }

}
