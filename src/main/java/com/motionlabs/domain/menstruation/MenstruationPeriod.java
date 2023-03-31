package com.motionlabs.domain.menstruation;

import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.menstruation.exception.InvalidMenstruationPeriodException;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int avgMenstruationPeriod;
    private int avgMenstruationDays;

    public MenstruationPeriod(Integer avgMenstruationPeriod, Integer avgMenstruationDays, Member member) {
        this(null, avgMenstruationPeriod, avgMenstruationDays, member);
    }

    public MenstruationPeriod(Long id, Integer avgMenstruationPeriod, Integer avgMenstruationDays, Member member) {
        this.id = id;
        this.avgMenstruationPeriod = validateMenstruationPeriod(avgMenstruationPeriod);
        this.avgMenstruationDays = validateMenstruationPeriod(avgMenstruationDays);
        this.member = member;
    }

    private int validateMenstruationPeriod(Integer avgValue) {
        if (Objects.isNull(avgValue) || avgValue < 0) {
            throw new InvalidMenstruationPeriodException();
        }

        return avgValue;
    }

}
