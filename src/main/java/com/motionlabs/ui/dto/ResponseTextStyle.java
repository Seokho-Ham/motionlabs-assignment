package com.motionlabs.ui.dto;

import lombok.Getter;

@Getter
public class ResponseTextStyle {

    private static final String RED_CODE = "#FF928B";
    private static final String BLUE_CODE = "#8BF8FF";

    private final String menstruationColor;
    private final String ovulationColor;
    private final boolean underlineStatus;

    private ResponseTextStyle(String menstruationColor, String ovulationColor,
        boolean underlineStatus) {
        this.menstruationColor = menstruationColor;
        this.ovulationColor = ovulationColor;
        this.underlineStatus = underlineStatus;
    }

    public static ResponseTextStyle historyType() {
        return new ResponseTextStyle(RED_CODE, BLUE_CODE, true);
    }

    public static ResponseTextStyle expectType() {
        return new ResponseTextStyle(RED_CODE, BLUE_CODE, false);
    }

}
