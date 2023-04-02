package com.motionlabs.application.menstruation;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.menstruation.MenstruationDates;
import com.motionlabs.domain.menstruation.MenstruationHistory;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.OvulationDates;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenstruationConverter {

    private final MenstruationAndOvulationCalculator calculator;

    public MenstruationHistory convertToEntity(Member member, MenstruationPeriod period,
        MenstruationHistoryRequest request) {

        MenstruationDates menstruationDates = getMenstruationDates(
            request.getMenstruationStartDate(), period);

        OvulationDates ovulationDates = getOvulationDates(request.getMenstruationStartDate(),
            period);

        return new MenstruationHistory(member, menstruationDates, ovulationDates);
    }

    private OvulationDates getOvulationDates(LocalDate menstruationStartDate,
        MenstruationPeriod period) {
        LocalDate ovulationStartDate = calculator.calculateOvulationStartDate(menstruationStartDate,
            period.getAvgMenstruationPeriod());
        LocalDate ovulationEndDate = calculator.calculateOvulationEndDate(menstruationStartDate,
            period.getAvgMenstruationPeriod());

        return new OvulationDates(ovulationStartDate, ovulationEndDate);
    }

    private MenstruationDates getMenstruationDates(LocalDate menstruationStartDate, MenstruationPeriod period) {
        return new MenstruationDates(menstruationStartDate, period.getAvgMenstruationDays());
    }

}
