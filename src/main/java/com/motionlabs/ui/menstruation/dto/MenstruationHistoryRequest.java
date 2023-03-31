package com.motionlabs.ui.menstruation.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenstruationHistoryRequest {

    private LocalDateTime menstruationStartDate;

    public MenstruationHistoryRequest(LocalDateTime menstruationStartDate) {
        this.menstruationStartDate = menstruationStartDate;
    }
}
