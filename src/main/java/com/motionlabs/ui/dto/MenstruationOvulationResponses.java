package com.motionlabs.ui.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class MenstruationOvulationResponses {

    private final ResponseTextStyle textStyle;
    private final  List<MenstruationOvulationResponse> results;

    private MenstruationOvulationResponses(ResponseTextStyle textStyle,
        List<MenstruationOvulationResponse> results) {
        this.textStyle = textStyle;
        this.results = results;
    }

    public static MenstruationOvulationResponses history(List<MenstruationOvulationResponse> results) {
        return new MenstruationOvulationResponses(ResponseTextStyle.historyType(), results);
    }

    public static MenstruationOvulationResponses expect(List<MenstruationOvulationResponse> results) {
        return new MenstruationOvulationResponses(ResponseTextStyle.expectType(), results);
    }

}
