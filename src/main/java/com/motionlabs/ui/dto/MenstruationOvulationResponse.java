package com.motionlabs.ui.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class MenstruationOvulationResponse {

    private final LocalDate menstruationStartDate;
    private final LocalDate menstruationEndDate;
    private final long menstruationDays;
    private final LocalDate ovulationStartDate;
    private final LocalDate ovulationEndDate;
    private final long ovulationDays;

    public MenstruationOvulationResponse(LocalDate menstruationStartDate,
        LocalDate menstruationEndDate, int menstruationDays,
        LocalDate ovulationStartDate, LocalDate ovulationEndDate,
        int ovulationDays) {
        this.menstruationStartDate = menstruationStartDate;
        this.menstruationEndDate = menstruationEndDate;
        this.menstruationDays = menstruationDays;
        this.ovulationStartDate = ovulationStartDate;
        this.ovulationEndDate = ovulationEndDate;
        this.ovulationDays = ovulationDays;
    }

}
