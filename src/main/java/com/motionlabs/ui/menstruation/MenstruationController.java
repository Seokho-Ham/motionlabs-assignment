package com.motionlabs.ui.menstruation;

import com.motionlabs.application.menstruation.MenstruationService;
import com.motionlabs.ui.dto.CommonResponseEntity;
import com.motionlabs.ui.dto.ResponseMessages;
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
        @RequestBody MenstruationPeriodRequest request) {

        menstruationService.registerPeriod(1L, request);

        return CommonResponseEntity.success(ResponseMessages.REGISTER_MENSTRUATION_PERIOD_SUCCESS);
    }


}
