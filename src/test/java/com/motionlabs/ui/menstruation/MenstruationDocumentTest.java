package com.motionlabs.ui.menstruation;

import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_HISTORY_SUCCESS;
import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_PERIOD_SUCCESS;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_HISTORY_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_PERIOD_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.createCommonNoDataSnippet;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.MemberRepository;
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
    private MenstruationPeriodRepository menstruationPeriodRepository;

    @BeforeEach
    void setUpTestData() {
        memberRepository.save(new Member("test-user", "test@gmail.com"));
        Member member2 = memberRepository.save(new Member("test-user2", "test2@gmail.com"));
        menstruationPeriodRepository.save(new MenstruationPeriod(10, 21, member2));
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

}
