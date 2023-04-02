package com.motionlabs.domain.menstruation;

import com.motionlabs.domain.menstruation.exception.InvalidOvulationDate;
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
public class OvulationDates {

    private LocalDate ovulationStartDate;
    private LocalDate ovulationEndDate;
    private int ovulationDays;

    public OvulationDates(LocalDate ovulationStartDate, LocalDate ovulationEndDate) {
        validateStartDate(ovulationStartDate);
        this.ovulationStartDate = ovulationStartDate;
        validateEndDate(ovulationEndDate);
        this.ovulationEndDate = ovulationEndDate;
        this.ovulationDays = calculateDays();
    }

    private void validateStartDate(LocalDate ovulationStartDate) {
        if (Objects.isNull(ovulationStartDate)) {
            throw new InvalidOvulationDate();
        }
    }

    private void validateEndDate(LocalDate ovulationEndDate) {
        if(this.ovulationStartDate.isAfter(ovulationEndDate)){
            throw new InvalidOvulationDate();
        }
    }

    private int calculateDays() {
        return Period.between(ovulationStartDate, ovulationEndDate)
            .getDays();
    }


}
