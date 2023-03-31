package com.motionlabs.ui.menstruation;

import static com.motionlabs.ui.menstruation.MenstruationSnippets.REGISTER_MENSTRUATION_PERIOD_REQUEST;
import static com.motionlabs.ui.menstruation.MenstruationSnippets.createCommonNoDataSnippet;
import static com.motionlabs.ui.dto.ResponseMessages.REGISTER_MENSTRUATION_PERIOD_SUCCESS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.motionlabs.ui.RestDocsTest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("문서 - 월경 API 문서 테스트")
public class MenstruationDocumentTest extends RestDocsTest {

    @Test
    @DisplayName("유저의 월경 주기 등록 요청이 정상적일 경우 200 응답을 반환한다.")
    void success_add_menstruation_period() {

        MenstruationPeriodRequest request = new MenstruationPeriodRequest(14, 28);

        given(this.spec)
            .filter(document(DOCUMENT_NAME_DEFAULT_FORMAT,
                REGISTER_MENSTRUATION_PERIOD_REQUEST,
                createCommonNoDataSnippet()))
            .cookie("userId", 1L)
            .body(request)

        .when()
            .post("/api/me/menstruation/period")

        .then()
            .statusCode(is(HttpStatus.OK.value()))
            .body("code", equalTo(HttpStatus.OK.value()))
            .body("message", equalTo(REGISTER_MENSTRUATION_PERIOD_SUCCESS.getMessage()));

    }

}
