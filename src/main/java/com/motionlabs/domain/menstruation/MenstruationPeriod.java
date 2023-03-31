package com.motionlabs.domain.menstruation;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.menstruation.exception.InvalidMenstruationPeriodException;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int avg_menstruation_period;
    private int avg_menstruation_days;

    public MenstruationPeriod(int avg_menstruation_period, int avg_menstruation_days, Member member) {
        this(null, avg_menstruation_period, avg_menstruation_days, member);
    }

    public MenstruationPeriod(Long id, int avg_menstruation_period, int avg_menstruation_days, Member member) {
        this.id = id;
        this.avg_menstruation_period = validateMenstruationPeriod(avg_menstruation_period);
        this.avg_menstruation_days = validateMenstruationPeriod(avg_menstruation_days);
        this.member = member;
    }

    private int validateMenstruationPeriod(Integer avg_value) {
        if (avg_value < 0) {
            throw new InvalidMenstruationPeriodException();
        }

        return avg_value;
    }

}
