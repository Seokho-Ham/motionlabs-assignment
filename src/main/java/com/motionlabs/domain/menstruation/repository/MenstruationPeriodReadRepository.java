package com.motionlabs.domain.menstruation.repository;

import com.motionlabs.domain.menstruation.MenstruationPeriod;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface MenstruationPeriodReadRepository {

    boolean existsByMemberId(Long memberId);

    Optional<MenstruationPeriod> findByMemberId(@Param("memberId") Long memberId);

}
