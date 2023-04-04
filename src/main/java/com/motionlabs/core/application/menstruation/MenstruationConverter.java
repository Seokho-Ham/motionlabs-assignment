package com.motionlabs.core.application.menstruation;

import com.motionlabs.core.domain.member.Member;
import com.motionlabs.core.domain.menstruation.MenstruationDates;
import com.motionlabs.core.domain.menstruation.MenstruationHistory;
import com.motionlabs.core.domain.menstruation.MenstruationPeriod;
import com.motionlabs.core.domain.menstruation.OvulationDates;
import com.motionlabs.ui.menstruation.dto.MenstruationOvulationResponse;
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

    public MenstruationOvulationResponse convertToResponse(LocalDate menstruationStartDate,
        MenstruationPeriod period) {

        LocalDate nextMenstruationStartDate = calculator.calculateNextMenstruationStartDate(
            menstruationStartDate, period.getAvgMenstruationPeriod());
        LocalDate nextMenstruationEndDate = nextMenstruationStartDate.plusDays(period.getAvgMenstruationDays());
        LocalDate ovulationStartDate = calculator.calculateOvulationStartDate(nextMenstruationStartDate,
            period.getAvgMenstruationPeriod());
        LocalDate ovulationEndDate = calculator.calculateOvulationEndDate(nextMenstruationStartDate,
            period.getAvgMenstruationPeriod());

        return new MenstruationOvulationResponse(
            nextMenstruationStartDate,
            nextMenstruationEndDate,
            calculator.calculateDateDuration(nextMenstruationStartDate, nextMenstruationEndDate),
            ovulationStartDate,
            ovulationEndDate,
            calculator.calculateDateDuration(ovulationStartDate, ovulationEndDate)
        );

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
