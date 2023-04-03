package com.motionlabs.integration.menstruation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.application.menstruation.exception.InvalidMenstruationDate;
import com.motionlabs.application.menstruation.exception.MenstruationHistoryNotFound;
import com.motionlabs.application.menstruation.exception.MenstruationPeriodNotRegistered;
import com.motionlabs.application.menstruation.exception.PeriodAlreadyRegisteredException;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.MenstruationPeriodRepository;
import com.motionlabs.integration.IntegrationTest;
import com.motionlabs.integration.menstruation.exception.DuplicatedMenstruationHistoryException;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import com.motionlabs.util.TestDataProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[통합] 월경 API 테스트")
public class MenstruationIntegrationTest extends IntegrationTest {

    private static long CLEAR_MEMBER_ID;
    private static long MEMBER_ID_WITH_PERIOD;
    private static long MEMBER_ID_WITH_ONE_HISTORY;
    private static long MEMBER_ID_WITH_HISTORIES;

    @Autowired
    private MenstruationService menstruationService;

    @Autowired
    private MenstruationPeriodRepository periodRepository;

    @Autowired
    private TestDataProvider testDataProvider;

    @BeforeEach
    void setUpTestData() {
        CLEAR_MEMBER_ID = testDataProvider.setClearMember();
        MEMBER_ID_WITH_PERIOD = testDataProvider.setMemberWithPeriod();
        MEMBER_ID_WITH_ONE_HISTORY = testDataProvider.setMemberWithOneHistory();
        MEMBER_ID_WITH_HISTORIES = testDataProvider.setMemberWithHistories();
    }

    @Nested
    @DisplayName("[월경 주기 등록 API]")
    class MenstruationPeriodRegister {

        @Test
        @DisplayName("월경 주기 요청이 정상적일 경우 생성된 월경주기 객체의 id를 반환한다.")
        void register_menstruation_period_success() {
            MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

            Long resultId = menstruationService.registerPeriod(CLEAR_MEMBER_ID, request);

            assertThat(resultId).isNotNull();
        }
        @Test
        @DisplayName("이미 사용자의 월경 주기 정보가 등록되어 있는 경우 예외를 반환한다.")
        void already_menstruation_period_registered() {
            MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

            menstruationService.registerPeriod(CLEAR_MEMBER_ID, request);

            assertThatThrownBy(() -> menstruationService.registerPeriod(CLEAR_MEMBER_ID, request))
                .isInstanceOf(PeriodAlreadyRegisteredException.class);
        }

    }

    @Nested
    @DisplayName("[월경 기록 등록 API]")
    class MenstruationHistoryRegister {
        @Test
        @DisplayName("월경 기록 등록 요청이 정상적일 경우 생성된 월경기록 객체의 id를 반환한다.")
        void register_menstruation_history_success() {
            MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

            Long resultId = menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, request);

            assertThat(resultId).isNotNull();
        }

        @Test
        @DisplayName("월경 기록 등록 요청이 정상적일 경우 회원의 평균 월경주기를 업데이트 한다.")
        void menstruation_period_success_and_update_average() {
            MenstruationHistoryRequest firstRequest = new MenstruationHistoryRequest("2022-11-01");
            MenstruationHistoryRequest secondRequest = new MenstruationHistoryRequest("2022-12-06");
            MenstruationHistoryRequest latestRequest = new MenstruationHistoryRequest("2023-02-05");
            MenstruationHistoryRequest currentRequest = new MenstruationHistoryRequest("2023-03-01");

            MenstruationPeriod beforeUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_PERIOD).get();

            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, firstRequest);
            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, secondRequest);
            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, latestRequest);
            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, currentRequest);

            MenstruationPeriod afterUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_PERIOD).get();

            assertThat(beforeUpdate.getAvgMenstruationPeriod()).isNotEqualTo(
                afterUpdate.getAvgMenstruationPeriod());

        }

        @Test
        @DisplayName("등록된 최근 월경 기록의 개수가 2개 미만일 경우 회원의 평균 월경주기를 업데이트하지 않는다.")
        void menstruation_history_count_is_less_than_two() {
            MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

            MenstruationPeriod beforeUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_PERIOD).get();

            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, request);

            MenstruationPeriod afterUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_PERIOD).get();

            assertThat(beforeUpdate.getAvgMenstruationPeriod()).isEqualTo(
                afterUpdate.getAvgMenstruationPeriod());
            assertThat(beforeUpdate.getAvgMenstruationDays()).isEqualTo(
                afterUpdate.getAvgMenstruationDays());

        }

        @Test
        @DisplayName("최근 등록된 월경 기록들간의 간격이 3개월 이상이라면 회원의 평균 월경주기를 업데이트 하지 않는다.")
        void latest_menstruation_history_is_before_three_month() {
            MenstruationHistoryRequest latestRequest = new MenstruationHistoryRequest("2022-10-01");
            MenstruationHistoryRequest currentRequest = new MenstruationHistoryRequest("2023-03-01");

            MenstruationPeriod beforeUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_PERIOD).get();

            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, latestRequest);
            menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, currentRequest);

            MenstruationPeriod afterUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_PERIOD).get();

            assertThat(beforeUpdate.getAvgMenstruationPeriod()).isEqualTo(
                afterUpdate.getAvgMenstruationPeriod());
        }

        @Test
        @DisplayName("동일한 기간에 중복되는 월경기록을 등록한다면 예외를 반환한다.")
        void duplicated_history() {
            MenstruationHistoryRequest currentRequest = new MenstruationHistoryRequest("2023-03-05");

            assertThatThrownBy(
                () -> menstruationService.registerHistory(MEMBER_ID_WITH_ONE_HISTORY, currentRequest))
                .isInstanceOf(DuplicatedMenstruationHistoryException.class);
        }

        @Test
        @DisplayName("회원의 월경 주기가 등록되어 있지 않으면 예외를 반환한다.")
        void menstruation_period_not_registered() {
            MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

            assertThatThrownBy(() -> menstruationService.registerHistory(CLEAR_MEMBER_ID, request))
                .isInstanceOf(MenstruationPeriodNotRegistered.class);
        }

        @Test
        @DisplayName("회원의 월경 주기가 현재 날짜보다 이후면 예외를 반환한다.")
        void invalid_menstruation_date() {
            MenstruationHistoryRequest request = new MenstruationHistoryRequest("9999-12-31");

            assertThatThrownBy(() -> menstruationService.registerHistory(MEMBER_ID_WITH_PERIOD, request))
                .isInstanceOf(InvalidMenstruationDate.class);

        }
    }

    @Nested
    @DisplayName("[월경 기록 삭제 API]")
    class MenstruationHistoryDelete {

        @Test
        @DisplayName("월경 기록 삭제 요청이 정상적일 경우 삭제된 데이터의 id를 반환한다.")
        void delete_history_success() {
            LocalDate targetStartDate = LocalDate.parse("2023-01-01",
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Long resultId = menstruationService.deleteHistory(MEMBER_ID_WITH_HISTORIES, targetStartDate);

            assertThat(resultId).isNotNull();
        }

        @Test
        @DisplayName("월경 기록 삭제가 정상적으로 이루어졌을 경우 유저의 평균 월경주기를 업데이트한다.")
        void update_menstruation_period() {
            LocalDate targetStartDate = LocalDate.parse("2023-01-01",
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            MenstruationPeriod beforeUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_HISTORIES).get();

            menstruationService.deleteHistory(MEMBER_ID_WITH_HISTORIES, targetStartDate);

            MenstruationPeriod afterUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_HISTORIES).get();

            assertThat(beforeUpdate.getAvgMenstruationPeriod()).isNotEqualTo(
                afterUpdate.getAvgMenstruationPeriod());
        }

        @Test
        @DisplayName("월경 기록 삭제시 유저의 최근 3개월 월경기록의 개수가 2개 미만일 경우 평균 월경주기를 업데이트 하지 않는다.")
        void do_not_update_menstruation_period() {

            LocalDate targetStartDate = LocalDate.parse("2023-03-01",
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            MenstruationPeriod beforeUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_ONE_HISTORY).get();

            menstruationService.deleteHistory(MEMBER_ID_WITH_ONE_HISTORY, targetStartDate);

            MenstruationPeriod afterUpdate = periodRepository.findByMemberId(
                MEMBER_ID_WITH_ONE_HISTORY).get();

            assertThat(beforeUpdate.getAvgMenstruationPeriod()).isEqualTo(
                afterUpdate.getAvgMenstruationPeriod());

        }

        @Test
        @DisplayName("이미 삭제되었거나 등록되지 않은 기록에 대한 요청일 경우 예외를 반환한다.")
        void not_found_history() {
            LocalDate targetStartDate = LocalDate.parse("9999-12-31",
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            assertThatThrownBy(() -> menstruationService.deleteHistory(MEMBER_ID_WITH_HISTORIES, targetStartDate))
                .isInstanceOf(MenstruationHistoryNotFound.class);
        }
    }


}
