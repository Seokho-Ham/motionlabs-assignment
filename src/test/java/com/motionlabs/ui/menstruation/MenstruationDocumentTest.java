package com.motionlabs.ui.menstruation;

import static com.motionlabs.ui.dto.ResponseMessages.DELETE_MENSTRUATION_HISTORY_SUCCESS;
import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_HISTORY_SUCCESS;
import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_PERIOD_SUCCESS;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.DELETE_MENSTRUATION_HISTORY_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_HISTORY_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_PERIOD_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.createCommonNoDataSnippet;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.motionlabs.application.menstruation.MenstruationConverter;
import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.MemberRepository;
import com.motionlabs.domain.menstruation.MenstruationHistory;
import com.motionlabs.domain.menstruation.MenstruationHistoryRepository;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.MenstruationPeriodRepository;
import com.motionlabs.ui.RestDocsTest;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("문서 - 월경 API 문서 테스트")
public class MenstruationDocumentTest extends RestDocsTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MenstruationPeriodRepository periodRepository;

    @Autowired
    private MenstruationHistoryRepository historyRepository;

    @Autowired
    private MenstruationConverter menstruationConverter;

    @BeforeEach
    void setUpTestData() {
        memberRepository.save(new Member("test-user", "test@gmail.com"));
        Member member2 = memberRepository.save(new Member("test-user2", "test2@gmail.com"));
        Member member3 = memberRepository.save(new Member("test-user3", "test3@gmail.com"));

        periodRepository.save(new MenstruationPeriod(10, 21, member2));
        MenstruationPeriod member3Period = periodRepository.save(
            new MenstruationPeriod(10, 21, member3));

        MenstruationHistory menstruationHistory = menstruationConverter.convertToEntity(member3,
            member3Period, new MenstruationHistoryRequest("2023-03-01"));
        historyRepository.save(menstruationHistory);

    }

    @Test
    @DisplayName("유저의 월경 주기 등록 요청이 정상적일 경우 200 응답을 반환한다.")
    void success_add_menstruation_period() {

        MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

        given(this.spec)
            .filter(document(DOCUMENT_NAME_DEFAULT_FORMAT,
                REGISTER_MENSTRUATION_PERIOD_REQUEST,
                createCommonNoDataSnippet()))
            .cookie("memberId", 1L)
            .body(request)

        .when()
            .post("/api/me/menstruation/period")

        .then()
            .statusCode(is(HttpStatus.OK.value()))
            .body("code", equalTo(HttpStatus.OK.value()))
            .body("message", equalTo(REGISTER_MENSTRUATION_PERIOD_SUCCESS.getMessage()));

    }

    @Test
    @DisplayName("유저의 월경 기록 등록 요청이 정상적일 경우 200 응답을 반환한다.")
    void success_add_menstruation_history() {
        MenstruationHistoryRequest request = new MenstruationHistoryRequest("2023-03-01");

        given(this.spec)
            .filter(document(DOCUMENT_NAME_DEFAULT_FORMAT,
                REGISTER_MENSTRUATION_HISTORY_REQUEST,
                createCommonNoDataSnippet()))
            .cookie("memberId", 2L)
            .body(request)

        .when()
            .post("/api/me/menstruation/history")

        .then()
            .statusCode(is(HttpStatus.OK.value()))
            .body("code", equalTo(HttpStatus.OK.value()))
            .body("message", equalTo(REGISTER_MENSTRUATION_HISTORY_SUCCESS.getMessage()));

    }

    @Test
    @DisplayName("유저의 월경 기록 삭제 요청이 정상적일 경우 200 응답을 반환한다.")
    void success_delete_menstruation_history() {

        given(this.spec)
            .filter(document(DOCUMENT_NAME_DEFAULT_FORMAT,
                DELETE_MENSTRUATION_HISTORY_REQUEST,
                createCommonNoDataSnippet()))
            .cookie("memberId", 3L)
            .param("targetStartDate", "2023-03-01")

        .when()
            .delete("/api/me/menstruation/history")

        .then()
            .statusCode(is(HttpStatus.OK.value()))
            .body("code", equalTo(HttpStatus.OK.value()))
            .body("message", equalTo(DELETE_MENSTRUATION_HISTORY_SUCCESS.getMessage()));

    }

}
