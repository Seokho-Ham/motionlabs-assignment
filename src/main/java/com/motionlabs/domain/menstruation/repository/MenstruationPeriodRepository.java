package com.motionlabs.domain.menstruation.repository;

import com.motionlabs.domain.menstruation.MenstruationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenstruationPeriodRepository extends JpaRepository<MenstruationPeriod, Long>, MenstruationPeriodReadRepository{

}
