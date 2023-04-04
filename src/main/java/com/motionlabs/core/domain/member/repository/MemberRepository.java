package com.motionlabs.core.domain.member.repository;

import com.motionlabs.core.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberReadRepository {

}
