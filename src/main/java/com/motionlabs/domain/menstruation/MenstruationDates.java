package com.motionlabs.domain.menstruation;

import com.motionlabs.application.menstruation.exception.InvalidMenstruationDate;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenstruationDates {

    private LocalDate menstruationStartDate;
    private LocalDate menstruationEndDate;
    private int menstruationDays;

    public MenstruationDates(LocalDate menstruationStartDate, int avgMenstruationDays) {
        validateStartDate(menstruationStartDate);
        this.menstruationStartDate = menstruationStartDate;
        this.menstruationEndDate = calculateEndDate(avgMenstruationDays);
        this.menstruationDays = calculateDays();
    }

    private void validateStartDate(LocalDate menstruationStartDate) {
        if (Objects.isNull(menstruationStartDate)
            || menstruationStartDate.isAfter(LocalDate.now())) {
            throw new InvalidMenstruationDate();
        }
    }

    private LocalDate calculateEndDate(int avgMenstruationDays) {
        return this.menstruationStartDate.plusDays(avgMenstruationDays);
    }

    private int calculateDays() {
        return Period.between(menstruationStartDate, menstruationEndDate)
            .getDays();
    }

}
