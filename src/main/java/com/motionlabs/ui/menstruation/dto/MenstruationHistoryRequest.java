package com.motionlabs.ui.menstruation.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenstruationHistoryRequest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private LocalDate menstruationStartDate;

    public MenstruationHistoryRequest(String menstruationStartDate) {
        this.menstruationStartDate = LocalDate.parse(menstruationStartDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

}
