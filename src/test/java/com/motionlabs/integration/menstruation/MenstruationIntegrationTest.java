package com.motionlabs.integration.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.domain.menstruation.exception.InvalidMenstruationPeriodException;
import com.motionlabs.integration.IntegrationTest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 - 월경 API 테스트")
public class MenstruationIntegrationTest extends IntegrationTest {

    @Autowired
    private MenstruationService menstruationService;

    @Test
    @DisplayName("월경 주기 요청이 정상적일 경우 생성된 월경주기 객체의 id를 반환한다.")
    void register_menstruation_period_success() {

        Long userId = 1L;
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(14, 28);

        Long resultId = menstruationService.registerPeriod(userId, request);

        assertThat(resultId).isNotNull();
    }

    @Test
    @DisplayName("월경 주기의 값이 올바르지 않을 경우 예외를 반환한다.")
    void invalid_avg_menstruation_period() {

        Long userId = 1L;
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(-1, 28);

        assertThatThrownBy(() -> menstruationService.registerPeriod(userId, request))
            .isInstanceOf(InvalidMenstruationPeriodException.class);
    }

    @Test
    @DisplayName("월경 기간의 값이 올바르지 않을 경우 예외를 반환한다.")
    void invalid_avg_menstruation_days() {

        Long userId = 1L;
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(14, -1);

        assertThatThrownBy(() -> menstruationService.registerPeriod(userId, request))
            .isInstanceOf(InvalidMenstruationPeriodException.class);
    }


}
