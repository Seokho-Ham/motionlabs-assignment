package com.motionlabs.core.application.menstruation;

import com.motionlabs.core.domain.menstruation.MenstruationDates;
import com.motionlabs.core.domain.menstruation.MenstruationHistory;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MenstruationAndOvulationCalculator {

    private static final int MAX_PERIOD = 45;
    private static final int MIN_PERIOD = 20;
    private static final int OVULATION_START_CALCULATE_DAYS = 19;
    private static final int OVULATION_END_CALCULATE_DAYS = 11;

    /**
     * 월경 평균 주기 계산 기능
     *
     * @param latestHistories 최근 6개월 간의 월경 기록
     */
    public int calculateMenstruationPeriodAverage(List<MenstruationHistory> latestHistories) {
        int totalPeriods = 0;

        for (int i = latestHistories.size() - 1; i > 0; i--) {
            MenstruationDates earlyDate = latestHistories.get(i).getMenstruationDates();
            MenstruationDates lateDate = latestHistories.get(i - 1).getMenstruationDates();

            totalPeriods += calculateDateDuration(
                earlyDate.getMenstruationStartDate(),
                lateDate.getMenstruationStartDate()
            );
        }

        int memberMenstruationPeriod = totalPeriods / (latestHistories.size() - 1);

        if (memberMenstruationPeriod < MIN_PERIOD || memberMenstruationPeriod > MAX_PERIOD) {
            return 0;
        }

        return memberMenstruationPeriod;
    }

    /**
     * 배란일 시작일 계산 메서드 공식: 현재 월경 시작일 + 평균 월경 주기 - 19
     *
     * @param menstruationStartDate 월경 시작일
     * @param avgMenstruationPeriod 평균 월경 주기
     */

    public LocalDate calculateOvulationStartDate(LocalDate menstruationStartDate,
        int avgMenstruationPeriod) {
        return calculateNextMenstruationStartDate(menstruationStartDate,
            avgMenstruationPeriod).minusDays(OVULATION_START_CALCULATE_DAYS);
    }

    /**
     * 배란일 종료일 계산 메서드 공식: 현재 월경 시작일 + 평균 월경 주기 - 11
     *
     * @param menstruationStartDate 월경 시작일
     * @param avgMenstruationPeriod 평균 월경 주기
     */

    public LocalDate calculateOvulationEndDate(LocalDate menstruationStartDate,
        int avgMenstruationPeriod) {
        return calculateNextMenstruationStartDate(menstruationStartDate,
            avgMenstruationPeriod).minusDays(OVULATION_END_CALCULATE_DAYS);
    }

    /**
     * 다음 예상 월경 시작일 계산 공식: 현재 월경 시작일 + 평균 월경 주기
     */
    public LocalDate calculateNextMenstruationStartDate(LocalDate menstruationStartDate,
        int avgMenstruationPeriod) {
        return menstruationStartDate.plusDays(avgMenstruationPeriod);
    }

    public int calculateDateDuration(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
}
