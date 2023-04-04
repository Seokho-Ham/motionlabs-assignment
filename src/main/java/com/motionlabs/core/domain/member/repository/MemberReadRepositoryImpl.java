package com.motionlabs.core.domain.member.repository;

import static com.motionlabs.domain.member.QMember.member;

import com.motionlabs.core.domain.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import javax.persistence.EntityManager;

public class MemberReadRepositoryImpl implements MemberReadRepository {

    private final JPAQueryFactory queryFactory;

    public MemberReadRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Member> findMemberById(Long memberId) {
        return Optional.ofNullable(
            queryFactory.select(member)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne()
        );
    }

}
