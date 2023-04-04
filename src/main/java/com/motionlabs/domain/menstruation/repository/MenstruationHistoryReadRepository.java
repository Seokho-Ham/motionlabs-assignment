package com.motionlabs.domain.menstruation.repository;

import com.motionlabs.domain.menstruation.MenstruationHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenstruationHistoryReadRepository {

    List<MenstruationHistory> findLatestHistoriesByMemberId(Long memberId, int maxTerm);

    boolean existsTargetDate(Long memberId, LocalDate targetDate);

    Optional<MenstruationHistory> findByTargetDate(Long memberId, LocalDate targetDate);

    List<MenstruationHistory> findAllHistories(Long memberId);

}
