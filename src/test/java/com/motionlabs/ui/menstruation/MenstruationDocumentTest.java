package com.motionlabs.ui.menstruation;

import static com.motionlabs.ui.dto.ResponseMessages.DELETE_MENSTRUATION_HISTORY_SUCCESS;
import static com.motionlabs.ui.dto.ResponseMessages.READ_ALL_MENSTRUATION_HISTORIES_SUCCESS;
import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_HISTORY_SUCCESS;
import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_PERIOD_SUCCESS;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.DELETE_MENSTRUATION_HISTORY_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.GET_MENSTRUATION_HISTORIES_RESPONSE;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_HISTORY_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_PERIOD_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.createCommonNoDataSnippet;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.motionlabs.ui.RestDocsTest;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import com.motionlabs.util.TestDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@DisplayName("문서 - 월경 API 문서 테스트")
public class MenstruationDocumentTest extends RestDocsTest {

    private static long CLEAR_MEMBER_ID;
    private static long MEMBER_ID_WITH_PERIOD;
    private static long MEMBER_ID_WITH_ONE_HISTORY;
    private static long MEMBER_ID_WITH_HISTORIES;

    @Autowired
    private TestDataProvider testDataProvider;

    @BeforeEach
    void setUpTestData() {
        CLEAR_MEMBER_ID = testDataProvider.setClearMember();
        MEMBER_ID_WITH_PERIOD = testDataProvider.setMemberWithPeriod();
        MEMBER_ID_WITH_ONE_HISTORY = testDataProvider.setMemberWithOneHistory();
        MEMBER_ID_WITH_HISTORIES = testDataProvider.setMemberWithHistories();
    }

    @Test
    @DisplayName("유저의 월경 주기 등록 요청이 정상적일 경우 200 응답을 반환한다.")
    void success_add_menstruation_period() {

        MenstruationPeriodRequest request = new MenstruationPeriodRequest(28, 14);

        given(this.spec)
            .filter(document(DOCUMENT_NAME_DEFAULT_FORMAT,
                REGISTER_MENSTRUATION_PERIOD_REQUEST,
                createCommonNoDataSnippet()))
            .cookie("memberId", CLEAR_MEMBER_ID)
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
            .cookie("memberId", MEMBER_ID_WITH_PERIOD)
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
            .cookie("memberId", MEMBER_ID_WITH_ONE_HISTORY)
            .param("targetStartDate", "2023-03-01")

        .when()
            .delete("/api/me/menstruation/history")

        .then()
            .statusCode(is(HttpStatus.OK.value()))
            .body("code", equalTo(HttpStatus.OK.value()))
            .body("message", equalTo(DELETE_MENSTRUATION_HISTORY_SUCCESS.getMessage()));

    }

    @Test
    @DisplayName("유저의 월경 기록 조회 요청이 정상적일 경우 200 응답과 기록 목록을 반환한다.")
    void success_get_all_menstruation_histories() {

        given(this.spec)
            .filter(document(DOCUMENT_NAME_DEFAULT_FORMAT, GET_MENSTRUATION_HISTORIES_RESPONSE))
            .cookie("memberId", MEMBER_ID_WITH_HISTORIES)
            .param("targetStartDate", "2023-03-01")

        .when()
            .get("/api/me/menstruation/histories")

        .then()
            .log().all()
            .statusCode(is(HttpStatus.OK.value()))
            .body("code", equalTo(HttpStatus.OK.value()))
            .body("message", equalTo(READ_ALL_MENSTRUATION_HISTORIES_SUCCESS.getMessage()));

    }


}
