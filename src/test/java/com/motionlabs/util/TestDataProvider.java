package com.motionlabs.util;

import com.motionlabs.application.menstruation.MenstruationConverter;
import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.MemberRepository;
import com.motionlabs.domain.menstruation.MenstruationHistory;
import com.motionlabs.domain.menstruation.MenstruationHistoryRepository;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.MenstruationPeriodRepository;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestDataProvider {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MenstruationPeriodRepository periodRepository;

    @Autowired
    private MenstruationHistoryRepository historyRepository;

    @Autowired
    private MenstruationConverter menstruationConverter;

    public Long setClearMember() {
        return memberRepository.save(new Member("test-user", "test@gmail.com"))
            .getId();
    }

    public Long setMemberWithPeriod() {
        Member member2 = memberRepository.save(new Member("test-user2", "test2@gmail.com"));
        periodRepository.save(new MenstruationPeriod(10, 21, member2));

        return member2.getId();
    }

    public Long setMemberWithOneHistory() {
        Member member3 = memberRepository.save(new Member("test-user3", "test3@gmail.com"));
        MenstruationPeriod member3Period = periodRepository.save(
            new MenstruationPeriod(10, 21, member3));
        MenstruationHistory menstruationHistory = menstruationConverter.convertToEntity(member3,
            member3Period, new MenstruationHistoryRequest("2023-03-01"));
        historyRepository.save(menstruationHistory);

        return member3.getId();
    }

    public Long setMemberWithHistories() {
        Member member4 = memberRepository.save(new Member("test-user4", "test4@gmail.com"));
        MenstruationPeriod member4Period = periodRepository.save(
            new MenstruationPeriod(10, 21, member4));

        MenstruationHistory menstruationHistory1 = menstruationConverter.convertToEntity(member4,
            member4Period, new MenstruationHistoryRequest("2023-01-01"));
        MenstruationHistory menstruationHistory2 = menstruationConverter.convertToEntity(member4,
            member4Period, new MenstruationHistoryRequest("2023-02-04"));
        MenstruationHistory menstruationHistory3 = menstruationConverter.convertToEntity(member4,
            member4Period, new MenstruationHistoryRequest("2023-03-15"));

        historyRepository.save(menstruationHistory1);
        historyRepository.save(menstruationHistory2);
        historyRepository.save(menstruationHistory3);

        return member4.getId();
    }

}
