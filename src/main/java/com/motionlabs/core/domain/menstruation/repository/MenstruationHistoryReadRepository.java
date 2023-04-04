package com.motionlabs.core.domain.menstruation.repository;

import com.motionlabs.core.domain.menstruation.MenstruationHistory;
import com.motionlabs.ui.menstruation.dto.MenstruationOvulationResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenstruationHistoryReadRepository {

    List<MenstruationHistory> findLatestHistoriesByMemberId(Long memberId, int maxTerm);

    boolean existsTargetDate(Long memberId, LocalDate targetDate);

    Optional<MenstruationHistory> findByTargetDate(Long memberId, LocalDate targetDate);

    List<MenstruationOvulationResponse> findAllHistories(Long memberId);

}
