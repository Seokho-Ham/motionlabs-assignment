package com.motionlabs.ui.menstruation.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class MenstruationHistoryRequest {

    private LocalDate menstruationStartDate;

    public MenstruationHistoryRequest(LocalDate menstruationStartDate) {
        this.menstruationStartDate = menstruationStartDate;
    }

}
