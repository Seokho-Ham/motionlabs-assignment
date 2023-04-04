package com.motionlabs.core.domain.member.repository;

import com.motionlabs.core.domain.member.Member;
import java.util.Optional;

public interface MemberReadRepository {

    Optional<Member> findMemberById(Long memberId);

}
