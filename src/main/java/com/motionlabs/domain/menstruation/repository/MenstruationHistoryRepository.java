package com.motionlabs.domain.menstruation.repository;

import com.motionlabs.domain.menstruation.MenstruationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenstruationHistoryRepository extends JpaRepository<MenstruationHistory, Long>, MenstruationHistoryReadRepository{

}
