package com.motionlabs.unit.domain.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.exception.InvalidMenstruationPeriodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 - 월경 주기 엔티티 테스트")
public class MenstruationPeriodTest {

    @Test
    @DisplayName("월경 주기의 값이 올바른 경우 객체를 생성한다.")
    void create_menstruation_period() {

        int avgPeriod = 28;
        int avgDays = 14;
        Member member = new Member("test-user", "test@gmail.com");
        MenstruationPeriod result = new MenstruationPeriod(avgPeriod, avgDays, member);

        assertThat(result.getAvgMenstruationPeriod()).isEqualTo(avgPeriod);
        assertThat(result.getAvgMenstruationDays()).isEqualTo(avgDays);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    @DisplayName("월경 주기의 값이 올바르지 않을 경우 예외를 반환한다.")
    void invalid_avg_menstruation_period(Integer period) {

        Member member = new Member("test-user", "test@gmail.com");

        assertThatThrownBy(() -> new MenstruationPeriod(period, 14, member))
            .isInstanceOf(InvalidMenstruationPeriodException.class);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    @DisplayName("월경 기간의 값이 올바르지 않을 경우 예외를 반환한다.")
    void invalid_avg_menstruation_days(Integer days) {

        Member member = new Member("test-user", "test@gmail.com");

        assertThatThrownBy(() -> new MenstruationPeriod(28, days, member))
            .isInstanceOf(InvalidMenstruationPeriodException.class);
    }


}
