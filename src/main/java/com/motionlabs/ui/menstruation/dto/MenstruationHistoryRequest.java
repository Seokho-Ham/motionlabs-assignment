package com.motionlabs.ui.menstruation.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MenstruationHistoryRequest {

    private final LocalDate menstruationStartDate;

    public MenstruationHistoryRequest(LocalDate menstruationStartDate) {
        this.menstruationStartDate = menstruationStartDate;
    }


}
