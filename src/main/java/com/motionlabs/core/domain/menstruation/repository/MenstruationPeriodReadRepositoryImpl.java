package com.motionlabs.core.domain.menstruation.repository;

import static com.motionlabs.core.domain.menstruation.QMenstruationPeriod.menstruationPeriod;

import com.motionlabs.core.domain.menstruation.MenstruationPeriod;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;

public class MenstruationPeriodReadRepositoryImpl implements MenstruationPeriodReadRepository{

    private final JPAQueryFactory queryFactory;

    public MenstruationPeriodReadRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public boolean existsByMemberId(Long memberId) {
        MenstruationPeriod period = queryFactory.select(menstruationPeriod)
            .from(menstruationPeriod)
            .where(memberIdEq(memberId))
            .fetchFirst();

        return period != null;
    }

    @Override
    public Optional<MenstruationPeriod> findByMemberId(Long memberId) {

        return Optional.ofNullable(
            queryFactory.select(menstruationPeriod)
                .from(menstruationPeriod)
                .where(memberIdEq(memberId))
                .fetchOne()
        );
    }

    private static BooleanExpression memberIdEq(Long memberId) {
        return menstruationPeriod.member.id.eq(memberId);
    }

}
