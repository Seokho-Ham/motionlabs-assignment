package com.motionlabs.unit.domain.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.domain.menstruation.OvulationDates;
import com.motionlabs.domain.menstruation.exception.InvalidOvulationDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[단위] 배란기 날짜정보를 담은 객체 테스트")
public class OvulationDatesTest {

    @Test
    @DisplayName("배란기 종료일이 시작일보다 빠른 날짜라면 예외를 반환한다.")
    void invalid_end_date() {

        LocalDate startDate = LocalDate.parse("2023-03-05", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse("2023-03-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        assertThatThrownBy(() -> new OvulationDates(startDate, endDate))
            .isInstanceOf(InvalidOvulationDate.class);

    }

    @Test
    @DisplayName("배란기 종료일이 시작일보다 빠른 날짜라면 예외를 반환한다.")
    void start_or_end_date_is_null() {

        LocalDate startDate = LocalDate.parse("2023-03-01",
            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse("2023-03-05",
            DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        assertThatThrownBy(() -> new OvulationDates(null, endDate))
            .isInstanceOf(InvalidOvulationDate.class);

        assertThatThrownBy(() -> new OvulationDates(startDate, null))
            .isInstanceOf(InvalidOvulationDate.class);

    }
}
