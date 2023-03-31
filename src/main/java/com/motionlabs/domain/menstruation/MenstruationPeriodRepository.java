package com.motionlabs.domain.menstruation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenstruationPeriodRepository extends JpaRepository<MenstruationPeriod, Long> {

    boolean existsByMemberId(Long memberId);
}
