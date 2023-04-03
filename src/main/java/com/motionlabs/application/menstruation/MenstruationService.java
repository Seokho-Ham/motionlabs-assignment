package com.motionlabs.application.menstruation;

import com.motionlabs.application.member.exception.MemberNotFoundException;
import com.motionlabs.application.menstruation.exception.MenstruationHistoryNotFound;
import com.motionlabs.application.menstruation.exception.MenstruationPeriodNotRegistered;
import com.motionlabs.application.menstruation.exception.PeriodAlreadyRegisteredException;
import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.member.repository.MemberRepository;
import com.motionlabs.domain.menstruation.MenstruationHistory;
import com.motionlabs.domain.menstruation.MenstruationPeriod;
import com.motionlabs.domain.menstruation.repository.MenstruationHistoryRepository;
import com.motionlabs.domain.menstruation.repository.MenstruationPeriodRepository;
import com.motionlabs.integration.menstruation.exception.DuplicatedMenstruationHistoryException;
import com.motionlabs.ui.dto.MemberMenstruationHistoryResponse;
import com.motionlabs.ui.dto.MenstruationOvulationResponse;
import com.motionlabs.ui.menstruation.dto.MenstruationHistoryRequest;
import com.motionlabs.ui.menstruation.dto.MenstruationPeriodRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenstruationService {

    private static final int MAX_MENSTRUATION_AVG = 4;

    private final MenstruationPeriodRepository periodRepository;
    private final MenstruationHistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final MenstruationConverter converter;
    private final MenstruationAndOvulationCalculator calculator;

    @Transactional(readOnly = true)
    public MemberMenstruationHistoryResponse getMenstruationHistories(Long memberId) {

        Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        MenstruationPeriod menstruationPeriod = periodRepository.findByMemberId(
                memberId)
            .orElseThrow(MenstruationPeriodNotRegistered::new);

        List<MenstruationOvulationResponse> histories = historyRepository.findAllHistories(
            memberId);

        return createMenstruationHistoryResponse(menstruationPeriod, histories);
    }

    @Transactional
    public Long registerPeriod(Long memberId, MenstruationPeriodRequest request) {

        Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        if (periodRepository.existsByMemberId(member.getId())) {
            throw new PeriodAlreadyRegisteredException();
        }

        MenstruationPeriod menstruationPeriod = periodRepository.save(
            request.convertToEntity(member));

        return menstruationPeriod.getId();
    }

    @Transactional
    public Long registerHistory(Long memberId, MenstruationHistoryRequest request) {

        Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        MenstruationPeriod menstruationPeriod = periodRepository.findByMemberId(
                memberId)
            .orElseThrow(MenstruationPeriodNotRegistered::new);
        MenstruationHistory menstruationHistory = converter.convertToEntity(member,
            menstruationPeriod, request);

        saveMenstruationHistory(request, menstruationHistory);
        updateMemberMenstruationPeriod(memberId, menstruationPeriod);

        return menstruationHistory.getId();
    }

    @Transactional
    public Long deleteHistory(Long memberId, LocalDate targetStartDate) {

        Member member = memberRepository.findMemberById(memberId)
            .orElseThrow(MemberNotFoundException::new);
        MenstruationPeriod menstruationPeriod = periodRepository.findByMemberId(
                memberId)
            .orElseThrow(MenstruationPeriodNotRegistered::new);

        MenstruationHistory menstruationHistory = historyRepository.findByTargetDate(memberId,
                targetStartDate)
            .orElseThrow(MenstruationHistoryNotFound::new);

        historyRepository.delete(menstruationHistory);

        updateMemberMenstruationPeriod(memberId, menstruationPeriod);

        return menstruationHistory.getId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void updateMemberMenstruationPeriod(Long memberId,
        MenstruationPeriod menstruationPeriod) {
        List<MenstruationHistory> latestHistories = historyRepository.findLatestHistoriesByMemberId(
            memberId, MAX_MENSTRUATION_AVG);

        if (latestHistories.size() < 2) {
            return;
        }

        int totalPeriods = calculator.calculateMenstruationPeriodAverage(latestHistories);
        System.out.println(totalPeriods);
        if (totalPeriods > 0) {
            menstruationPeriod.updatePeriodAverage(totalPeriods);
        }
    }

    private void saveMenstruationHistory(MenstruationHistoryRequest request,
        MenstruationHistory menstruationHistory) {
        if (historyRepository.existsTargetDate(menstruationHistory.getMember().getId(),
            request.getMenstruationStartDate())) {
            throw new DuplicatedMenstruationHistoryException();
        }

        historyRepository.save(menstruationHistory);
    }

    private MemberMenstruationHistoryResponse createMenstruationHistoryResponse(
        MenstruationPeriod menstruationPeriod, List<MenstruationOvulationResponse> histories) {
        if (histories.isEmpty()) {
            return MemberMenstruationHistoryResponse.emptyResponse();
        }

        List<MenstruationOvulationResponse> expects = createExpectMenstruationResponse(
            histories, menstruationPeriod);

        return MemberMenstruationHistoryResponse.response(histories, expects);
    }

    private List<MenstruationOvulationResponse> createExpectMenstruationResponse(
        List<MenstruationOvulationResponse> histories, MenstruationPeriod period) {

        List<MenstruationOvulationResponse> result = new ArrayList<>();
        MenstruationOvulationResponse latest = histories.get(histories.size() - 1);

        for (int i = 0; i < 3; i++) {
            MenstruationOvulationResponse response = converter.convertToResponse(
                latest.getMenstruationStartDate(), period);
            result.add(response);
            latest = response;
        }

        return result;
    }

}
