package com.motionlabs.ui.menstruation;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import java.util.ArrayList;
import java.util.List;
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

    static Snippet createCommonDataSnippet(List<FieldDescriptor> fieldDescriptors) {
        List<FieldDescriptor> responseDescriptors = new ArrayList<>();
        responseDescriptors.add(COMMON_RESPONSE_CODE);
        responseDescriptors.addAll(fieldDescriptors);
        responseDescriptors.add(COMMON_RESPONSE_MESSAGE);

        return requestFields(responseDescriptors);
    }


    Snippet REGISTER_MENSTRUATION_PERIOD_REQUEST = requestFields(
        fieldWithPath("avg_menstruation_period").type(JsonFieldType.NUMBER).description("월경 주기 / (일 단위)"),
        fieldWithPath("avg_menstruation_days").type(JsonFieldType.NUMBER).description("월경 기간 / (일 단위)")
    );


}
