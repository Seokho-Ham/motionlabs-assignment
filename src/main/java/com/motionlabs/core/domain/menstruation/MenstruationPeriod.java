package com.motionlabs.core.domain.menstruation;

import com.motionlabs.core.domain.member.Member;
import com.motionlabs.core.domain.menstruation.exception.InvalidMenstruationPeriodException;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenstruationPeriod {

    private static final int MAX_PERIOD = 45;
    private static final int MIN_PERIOD = 20;
    private static final int MAX_DAYS = 8;
    private static final int MIN_DAYS = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int avgMenstruationPeriod;
    private int avgMenstruationDays;

    public MenstruationPeriod(Integer avgMenstruationPeriod, Integer avgMenstruationDays,
        Member member) {
        this(null, avgMenstruationPeriod, avgMenstruationDays, member);
    }

    public MenstruationPeriod(Long id, Integer avgMenstruationPeriod, Integer avgMenstruationDays,
        Member member) {
        this.id = id;
        validateMenstruationPeriod(avgMenstruationPeriod);
        this.avgMenstruationPeriod = avgMenstruationPeriod;
        validateMenstruationDays(avgMenstruationDays);
        this.avgMenstruationDays = avgMenstruationDays;
        this.member = member;
    }

    private void validateMenstruationDays(Integer avgMenstruationDays) {
        if (Objects.isNull(avgMenstruationDays)
            || avgMenstruationDays < MIN_DAYS
            || avgMenstruationDays > MAX_DAYS) {
            throw new InvalidMenstruationPeriodException();
        }
    }

    private void validateMenstruationPeriod(Integer avgMenstruationPeriod) {
        if (Objects.isNull(avgMenstruationPeriod)
            || avgMenstruationPeriod < MIN_PERIOD
            || avgMenstruationPeriod > MAX_PERIOD) {
            throw new InvalidMenstruationPeriodException();
        }
    }

    public void updatePeriodAverage(int totalPeriods) {
        this.avgMenstruationPeriod = totalPeriods;
    }

}
