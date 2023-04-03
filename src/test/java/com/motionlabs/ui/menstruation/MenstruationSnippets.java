package com.motionlabs.ui.menstruation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface MenstruationSnippets {

    FieldDescriptor COMMON_RESPONSE_CODE = fieldWithPath("code")
        .type(JsonFieldType.NUMBER)
        .description("응답 상태코드");

    FieldDescriptor COMMON_RESPONSE_MESSAGE = fieldWithPath("message")
        .type(JsonFieldType.STRING)
        .description("응답 메세지");

    FieldDescriptor COMMON_RESPONSE_DATA =
        fieldWithPath("data")
            .type(JsonFieldType.NULL)
            .description("응답 데이터");

    static Snippet createCommonNoDataSnippet() {
        return responseFields(
            COMMON_RESPONSE_CODE,
            COMMON_RESPONSE_DATA,
            COMMON_RESPONSE_MESSAGE
        );
    }
    
    Snippet REGISTER_MENSTRUATION_PERIOD_REQUEST = requestFields(
        fieldWithPath("avgMenstruationPeriod").type(JsonFieldType.NUMBER).description("월경 주기 / (일 단위)"),
        fieldWithPath("avgMenstruationDays").type(JsonFieldType.NUMBER).description("월경 기간 / (일 단위)")
    );

    Snippet REGISTER_MENSTRUATION_HISTORY_REQUEST = requestFields(
        fieldWithPath("menstruationStartDate").type(JsonFieldType.ARRAY)
            .description("월경 시작일 / 형식: yyyy-MM-dd")
    );

    Snippet DELETE_MENSTRUATION_HISTORY_REQUEST = requestParameters(
        parameterWithName("targetStartDate").description("삭제할 월경 시작일")
    );

    Snippet GET_MENSTRUATION_HISTORIES_RESPONSE = responseFields(
        COMMON_RESPONSE_CODE,
        fieldWithPath("data.history.textStyle.menstruationColor").description("월경 기간 표시 색상"),
        fieldWithPath("data.history.textStyle.ovulationColor").description("배란기 기간 표시 색상"),
        fieldWithPath("data.history.textStyle.underlineStatus").description("밑줄 여부"),
        fieldWithPath("data.history.results[].menstruationStartDate").description("월경 시작일"),
        fieldWithPath("data.history.results[].menstruationEndDate").description("월경 종료일"),
        fieldWithPath("data.history.results[].menstruationDays").description("월경 기간"),
        fieldWithPath("data.history.results[].ovulationStartDate").description("배란기 시작일"),
        fieldWithPath("data.history.results[].ovulationEndDate").description("배란기 종료일"),
        fieldWithPath("data.history.results[].ovulationDays").description("배란기 기간"),
        fieldWithPath("data.expects.textStyle.menstruationColor").description("예상 월경 기간 표시 색상"),
        fieldWithPath("data.expects.textStyle.ovulationColor").description("예상 배란기 기간 표시 색상"),
        fieldWithPath("data.expects.textStyle.underlineStatus").description("예상 밑줄 여부"),
        fieldWithPath("data.expects.results[].menstruationStartDate").description("예상 월경 시작일"),
        fieldWithPath("data.expects.results[].menstruationEndDate").description("예상 월경 종료일"),
        fieldWithPath("data.expects.results[].menstruationDays").description("예상 월경 기간"),
        fieldWithPath("data.expects.results[].ovulationStartDate").description("예상 배란기 시작일"),
        fieldWithPath("data.expects.results[].ovulationEndDate").description("예상 배란기 종료일"),
        fieldWithPath("data.expects.results[].ovulationDays").description("예상 배란기 기간"),
        COMMON_RESPONSE_MESSAGE
    );


}
