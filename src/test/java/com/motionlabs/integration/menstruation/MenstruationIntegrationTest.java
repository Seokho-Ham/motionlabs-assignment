package com.motionlabs.integration.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.application.menstruation.exception.PeriodAlreadyRegisteredException;
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
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

        Long resultId = menstruationService.registerPeriod(userId, request);

        assertThat(resultId).isNotNull();
    }

    @Test
    @DisplayName("이미 사용자의 월경 주기 정보가 등록되어 있는 경우 예외를 반환한다.")
    void already_menstruation_period_registered() {

        Long userId = 1L;
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

        menstruationService.registerPeriod(userId, request);

        assertThatThrownBy(() -> menstruationService.registerPeriod(userId, request))
            .isInstanceOf(PeriodAlreadyRegisteredException.class);
    }

}
