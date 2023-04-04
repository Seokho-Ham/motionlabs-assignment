package com.motionlabs.core.domain.menstruation.repository;

import com.motionlabs.core.domain.menstruation.MenstruationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenstruationPeriodRepository extends JpaRepository<MenstruationPeriod, Long>, MenstruationPeriodReadRepository{

}
