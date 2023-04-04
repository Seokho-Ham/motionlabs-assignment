package com.motionlabs.ui.menstruation;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.ui.dto.CommonResponseEntity;
import com.motionlabs.ui.menstruation.dto.MemberMenstruationHistoryResponse;
import com.motionlabs.ui.dto.ResponseMessages;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me/menstruation")
@RequiredArgsConstructor
public class MenstruationController {

    private final MenstruationService menstruationService;

    @GetMapping("/histories")
    public CommonResponseEntity<MemberMenstruationHistoryResponse> getMenstruationHistories(@MemberId Long memberId) {
        return CommonResponseEntity.success(menstruationService.getMenstruationHistories(memberId),
            ResponseMessages.READ_ALL_MENSTRUATION_HISTORIES_SUCCESS);
    }

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

    @DeleteMapping("/history")
    public CommonResponseEntity<Void> deleteMenstruationHistory(
        @MemberId Long memberId,
        @RequestParam("targetStartDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate targetStartDate) {

        menstruationService.deleteHistory(memberId, targetStartDate);

        return CommonResponseEntity.success(ResponseMessages.DELETE_MENSTRUATION_HISTORY_SUCCESS);
    }


}
