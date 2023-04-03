package com.motionlabs.domain.menstruation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenstruationHistoryRepository extends JpaRepository<MenstruationHistory, Long> {

    @Query("select h from MenstruationHistory h "
        + "where h.member.id = :memberId "
        + "order by h.menstruationDates.menstruationStartDate ")
    List<MenstruationHistory> findLatestHistoriesByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select h from MenstruationHistory h "
        + "where h.member.id = :memberId "
        + "and h.menstruationDates.menstruationStartDate <= :targetDate "
        + "and h.menstruationDates.menstruationEndDate >= :targetDate")
    Optional<MenstruationHistory> existsTargetDate(@Param("memberId") Long memberId,
        @Param("targetDate") LocalDate targetDate);

    @Query("select h from MenstruationHistory h "
        + "where h.member.id = :memberId "
        + "and h.menstruationDates.menstruationStartDate = :targetDate")
    Optional<MenstruationHistory> findByTargetDate(@Param("memberId") Long memberId,
        @Param("targetDate") LocalDate targetDate);


}
