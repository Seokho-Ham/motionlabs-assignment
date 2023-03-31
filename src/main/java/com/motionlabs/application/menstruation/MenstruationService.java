package com.motionlabs.application.menstruation;

import com.motionlabs.application.member.exception.MemberNotFoundException;
import com.motionlabs.application.menstruation.exception.PeriodAlreadyRegisteredException;
import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.MemberRepository;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.MenstruationPeriodRepository;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenstruationService {

    private final MenstruationPeriodRepository menstruationPeriodRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long registerPeriod(Long userId, MenstruationPeriodRequest request) {

        Member member = memberRepository.findById(userId)
            .orElseThrow(MemberNotFoundException::new);

        if (menstruationPeriodRepository.existsByMemberId(member.getId())) {
            throw new PeriodAlreadyRegisteredException();
        }

        MenstruationPeriod menstruationPeriod = menstruationPeriodRepository.save(
            request.convertToEntity(member));

        return menstruationPeriod.getId();
    }

}
