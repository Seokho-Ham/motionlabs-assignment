package com.motionlabs.unit.domain.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.core.application.menstruation.exception.InvalidMenstruationDate;
import com.motionlabs.core.domain.menstruation.MenstruationDates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[단위] 월경 날짜정보를 담은 객체 테스트")
public class MenstruationDatesTest {

    @Test
    @DisplayName("현재 날짜보다 이후의 날짜가 입력될 경우 예외를 반환한다.")
    void invalid_date() {
        LocalDate date = LocalDate.parse("9999-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertThatThrownBy(() -> new MenstruationDates(date, 10))
            .isInstanceOf(InvalidMenstruationDate.class);
    }

    @Test
    @DisplayName("null이 입력될 경우 예외를 반환한다.")
    void null_date() {
        assertThatThrownBy(() -> new MenstruationDates(null, 10))
            .isInstanceOf(InvalidMenstruationDate.class);
    }


}
