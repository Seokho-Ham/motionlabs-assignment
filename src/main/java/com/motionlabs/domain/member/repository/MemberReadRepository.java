package com.motionlabs.domain.member.repository;

import com.motionlabs.domain.member.Member;
import java.util.Optional;

public interface MemberReadRepository {

    Optional<Member> findMemberById(Long memberId);

}
