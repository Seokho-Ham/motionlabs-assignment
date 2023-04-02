package com.motionlabs.domain.menstruation;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenstruationPeriodRepository extends JpaRepository<MenstruationPeriod, Long> {

    boolean existsByMemberId(Long memberId);

    @Query("select p from MenstruationPeriod p where p.member.id = :memberId")
    Optional<MenstruationPeriod> findByMemberId(@Param("memberId") Long memberId);

}
