package com.motionlabs.domain.menstruation.repository;

import com.motionlabs.domain.menstruation.MenstruationHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface MenstruationHistoryReadRepository {

    List<MenstruationHistory> findLatestHistoriesByMemberId(@Param("memberId") Long memberId, int maxTerm);

    boolean existsTargetDate(@Param("memberId") Long memberId,
        @Param("targetDate") LocalDate targetDate);

    Optional<MenstruationHistory> findByTargetDate(@Param("memberId") Long memberId,
        @Param("targetDate") LocalDate targetDate);


}
