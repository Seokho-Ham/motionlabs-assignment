package com.motionlabs.integration.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.application.menstruation.exception.InvalidMenstruationDate;
import com.motionlabs.application.menstruation.exception.MenstruationPeriodNotRegistered;
import com.motionlabs.application.menstruation.exception.PeriodAlreadyRegisteredException;
import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.MemberRepository;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.MenstruationPeriodRepository;
import com.motionlabs.integration.IntegrationTest;
import com.motionlabs.integration.menstruation.exception.DuplicatedMenstruationHistoryException;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 - 월경 API 테스트")
public class MenstruationIntegrationTest extends IntegrationTest {

    private static final long NEW_MEMBER_ID = 1L;
    private static final long EXISTING_MEMBER_ID = 2L;

    @Autowired
    private MenstruationService menstruationService;

    @Autowired
    private MenstruationPeriodRepository menstruationPeriodRepository;

    @Autowired
    private MemberRepository memberRepository;

    //--- 월경 주기 등록 테스트

    @BeforeEach
    void setUpTestData() {
        memberRepository.save(new Member("test-user", "test@gmail.com"));
        Member member2 = memberRepository.save(new Member("test-user2", "test2@gmail.com"));
        menstruationPeriodRepository.save(new MenstruationPeriod(10, 21, member2));
    }

    @Test
    @DisplayName("[주기 등록 API] - 월경 주기 요청이 정상적일 경우 생성된 월경주기 객체의 id를 반환한다.")
    void register_menstruation_period_success() {
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

        Long resultId = menstruationService.registerPeriod(NEW_MEMBER_ID, request);

        assertThat(resultId).isNotNull();
    }

    @Test
    @DisplayName("[주기 등록 API] - 이미 사용자의 월경 주기 정보가 등록되어 있는 경우 예외를 반환한다.")
    void already_menstruation_period_registered() {
        MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

        menstruationService.registerPeriod(NEW_MEMBER_ID, request);

        assertThatThrownBy(() -> menstruationService.registerPeriod(NEW_MEMBER_ID, request))
            .isInstanceOf(PeriodAlreadyRegisteredException.class);
    }

    //--- 월경 기록 등록 테스트

    @Test
    @DisplayName("[기록 등록 API] - 월경 기록 등록 요청이 정상적일 경우 생성된 월경기록 객체의 id를 반환한다.")
    void register_menstruation_history_success() {
        MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

        Long resultId = menstruationService.registerHistory(EXISTING_MEMBER_ID, request);

        assertThat(resultId).isNotNull();
    }

    @Test
    @DisplayName("[기록 등록 API] - 월경 기록 등록 요청이 정상적일 경우 회원의 평균 월경주기를 업데이트 한다.")
    void menstruation_period_success_and_update_average() {
        MenstruationHistoryRequest firstRequest = new MenstruationHistoryRequest("2022-11-01");
        MenstruationHistoryRequest secondRequest = new MenstruationHistoryRequest("2022-12-06");
        MenstruationHistoryRequest latestRequest = new MenstruationHistoryRequest("2023-02-05");
        MenstruationHistoryRequest currentRequest = new MenstruationHistoryRequest("2023-03-01");

        MenstruationPeriod beforeUpdate = menstruationPeriodRepository.findByMemberId(
            EXISTING_MEMBER_ID).get();

        menstruationService.registerHistory(EXISTING_MEMBER_ID, firstRequest);
        menstruationService.registerHistory(EXISTING_MEMBER_ID, secondRequest);
        menstruationService.registerHistory(EXISTING_MEMBER_ID, latestRequest);
        menstruationService.registerHistory(EXISTING_MEMBER_ID, currentRequest);

        MenstruationPeriod afterUpdate = menstruationPeriodRepository.findByMemberId(
            EXISTING_MEMBER_ID).get();

        assertThat(beforeUpdate.getAvgMenstruationPeriod()).isNotEqualTo(
            afterUpdate.getAvgMenstruationPeriod());

    }

    @Test
    @DisplayName("[기록 등록 API] - 등록된 최근 월경 기록의 개수가 2개 미만일 경우 회원의 평균 월경주기를 업데이트하지 않는다.")
    void menstruation_history_count_is_less_than_two() {
        MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

        MenstruationPeriod beforeUpdate = menstruationPeriodRepository.findByMemberId(
            EXISTING_MEMBER_ID).get();

        menstruationService.registerHistory(EXISTING_MEMBER_ID, request);

        MenstruationPeriod afterUpdate = menstruationPeriodRepository.findByMemberId(
            EXISTING_MEMBER_ID).get();

        assertThat(beforeUpdate.getAvgMenstruationPeriod()).isEqualTo(
            afterUpdate.getAvgMenstruationPeriod());
        assertThat(beforeUpdate.getAvgMenstruationDays()).isEqualTo(
            afterUpdate.getAvgMenstruationDays());

    }

    @Test
    @DisplayName("[기록 등록 API] - 최근 등록된 월경 기록과 현재 등록하는 기록의 간격이 3개월 이상이라면 회원의 평균 월경주기를 업데이트 하지 않는다.")
    void latest_menstruation_history_is_before_three_month() {
        MenstruationHistoryRequest latestRequest = new MenstruationHistoryRequest("2022-10-01");
        MenstruationHistoryRequest currentRequest = new MenstruationHistoryRequest("2023-03-01");

        MenstruationPeriod beforeUpdate = menstruationPeriodRepository.findByMemberId(
            EXISTING_MEMBER_ID).get();

        menstruationService.registerHistory(EXISTING_MEMBER_ID, latestRequest);
        menstruationService.registerHistory(EXISTING_MEMBER_ID, currentRequest);

        MenstruationPeriod afterUpdate = menstruationPeriodRepository.findByMemberId(
            EXISTING_MEMBER_ID).get();

        assertThat(beforeUpdate.getAvgMenstruationPeriod()).isEqualTo(
            afterUpdate.getAvgMenstruationPeriod());
    }

    @Test
    @DisplayName("[기록 등록 API] - 동일한 기간에 중복되는 월경기록을 등록한다면 예외를 반환한다.")
    void duplicated_history() {
        MenstruationHistoryRequest latestRequest = new MenstruationHistoryRequest("2023-03-01");
        MenstruationHistoryRequest currentRequest = new MenstruationHistoryRequest("2023-03-05");

        menstruationService.registerHistory(EXISTING_MEMBER_ID, latestRequest);

        assertThatThrownBy(
            () -> menstruationService.registerHistory(EXISTING_MEMBER_ID, currentRequest))
            .isInstanceOf(DuplicatedMenstruationHistoryException.class);
    }

    @Test
    @DisplayName("[기록 등록 API] - 회원의 월경 주기가 등록되어 있지 않으면 예외를 반환한다.")
    void menstruation_period_not_registered() {
        MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

        assertThatThrownBy(() -> menstruationService.registerHistory(NEW_MEMBER_ID, request))
            .isInstanceOf(MenstruationPeriodNotRegistered.class);
    }

    @Test
    @DisplayName("[기록 등록 API] - 회원의 월경 주기가 현재 날짜보다 이후면 예외를 반환한다.")
    void invalid_menstruation_date() {
        MenstruationHistoryRequest request = new MenstruationHistoryRequest("9999-12-31");

        assertThatThrownBy(() -> menstruationService.registerHistory(EXISTING_MEMBER_ID, request))
            .isInstanceOf(InvalidMenstruationDate.class);

    }

}
