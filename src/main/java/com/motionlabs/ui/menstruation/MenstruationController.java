package com.motionlabs.ui.menstruation;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.ui.dto.CommonResponseEntity;
import com.motionlabs.ui.dto.ResponseMessages;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me/menstruation")
@RequiredArgsConstructor
public class MenstruationController {

    private final MenstruationService menstruationService;

    @PostMapping("/period")
    public CommonResponseEntity<Void> registerMenstruationPeriod(
        @MemberId Long memberId,
        @RequestBody MenstruationPeriodRequest request) {

        menstruationService.registerPeriod(memberId, request);

        return CommonResponseEntity.success(ResponseMessages.REGISTER_MENSTRUATION_PERIOD_SUCCESS);
    }

    @PostMapping("/history")
    public CommonResponseEntity<Void> registerMenstruationHistory(
        @MemberId Long memberId,
        @RequestBody MenstruationHistoryRequest request) {

        menstruationService.registerHistory(memberId, request);

        return CommonResponseEntity.success(ResponseMessages.REGISTER_MENSTRUATION_HISTORY_SUCCESS);
    }


}
