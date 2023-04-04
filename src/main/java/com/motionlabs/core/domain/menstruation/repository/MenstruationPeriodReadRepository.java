package com.motionlabs.core.domain.menstruation.repository;

import com.motionlabs.core.domain.menstruation.MenstruationPeriod;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface MenstruationPeriodReadRepository {

    boolean existsByMemberId(Long memberId);

    Optional<MenstruationPeriod> findByMemberId(@Param("memberId") Long memberId);

}
