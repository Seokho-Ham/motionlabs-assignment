package com.motionlabs.ui.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberMenstruationHistoryResponse {

    private final MenstruationOvulationResponses history;
    private final MenstruationOvulationResponses expects;

    private MemberMenstruationHistoryResponse(MenstruationOvulationResponses history,
        MenstruationOvulationResponses expects) {
        this.history = history;
        this.expects = expects;
    }

    public static MemberMenstruationHistoryResponse emptyResponse() {
        return new MemberMenstruationHistoryResponse(
            MenstruationOvulationResponses.history(List.of()),
            MenstruationOvulationResponses.expect(List.of()));
    }

    public static MemberMenstruationHistoryResponse response(
        List<MenstruationOvulationResponse> histories,
        List<MenstruationOvulationResponse> expects) {
        return new MemberMenstruationHistoryResponse(
            MenstruationOvulationResponses.history(histories),
            MenstruationOvulationResponses.expect(expects));
    }
}
