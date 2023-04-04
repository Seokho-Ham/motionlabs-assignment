package com.motionlabs.domain.menstruation.repository;

import static com.motionlabs.domain.menstruation.QMenstruationHistory.menstruationHistory;

import com.motionlabs.domain.menstruation.MenstruationHistory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class MenstruationHistoryReadRepositoryImpl implements MenstruationHistoryReadRepository {

    private final JPAQueryFactory queryFactory;

    public MenstruationHistoryReadRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<MenstruationHistory> findLatestHistoriesByMemberId(Long memberId,
        int maxTerm) {

        return queryFactory.select(menstruationHistory)
            .from(menstruationHistory)
            .where(memberIdEq(memberId))
            .orderBy(startDateDesc())
            .limit(maxTerm)
            .fetch();
    }

    @Override
    public boolean existsTargetDate(Long memberId, LocalDate targetDate) {

        MenstruationHistory menstruationHistoryResult = queryFactory.select(menstruationHistory)
            .from(menstruationHistory)
            .where(
                memberIdEq(memberId),
                startDateBefore(targetDate),
                endDateAfter(targetDate))
            .fetchFirst();

        return menstruationHistoryResult != null;
    }

    @Override
    public Optional<MenstruationHistory> findByTargetDate(Long memberId, LocalDate targetDate) {

        MenstruationHistory menstruationHistoryResult = queryFactory.select(menstruationHistory)
            .from(menstruationHistory)
            .where(memberIdEq(memberId),
                dateEq(targetDate))
            .fetchOne();

        return Optional.ofNullable(menstruationHistoryResult);
    }

    @Override
    public List<MenstruationHistory> findAllHistories(Long memberId) {
        return queryFactory.select(menstruationHistory)
            .from(menstruationHistory)
            .where(memberIdEq(memberId))
            .orderBy(startDateAsc())
            .fetch();

    }

    private BooleanExpression dateEq(LocalDate targetDate) {
        return menstruationHistory.menstruationDates.menstruationStartDate.eq(targetDate);
    }

    private static BooleanExpression startDateBefore(LocalDate targetDate) {
        return menstruationHistory.menstruationDates.menstruationStartDate.loe(targetDate);
    }

    private static BooleanExpression endDateAfter(LocalDate targetDate) {
        return menstruationHistory.menstruationDates.menstruationEndDate.goe(targetDate);
    }

    private static BooleanExpression memberIdEq(Long memberId) {
        return menstruationHistory.member.id.eq(memberId);
    }

    private static OrderSpecifier<LocalDate> startDateAsc() {
        return menstruationHistory.menstruationDates.menstruationStartDate.asc();
    }

    private static OrderSpecifier<LocalDate> startDateDesc() {
        return menstruationHistory.menstruationDates.menstruationStartDate.desc();
    }

}
