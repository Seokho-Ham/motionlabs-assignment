package com.motionlabs.domain.menstruation;

import com.motionlabs.domain.common.BaseTimeEntity;
import com.motionlabs.domain.member.Member;
import com.motionlabs.domain.menstruation.exception.InvalidMenstruationHistory;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update menstruation_history set deleted_at = current_timestamp where id = ?")
@Where(clause = "deleted_at is null")
public class MenstruationHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private MenstruationDates menstruationDates;

    @Embedded
    private OvulationDates ovulationDates;

    public MenstruationHistory(Member member, MenstruationDates menstruationDates,
        OvulationDates ovulationDates) {
        if (Objects.isNull(member) || Objects.isNull(menstruationDates) || Objects.isNull(
            ovulationDates)) {
            throw new InvalidMenstruationHistory();
        }

        this.member = member;
        this.menstruationDates = menstruationDates;
        this.ovulationDates = ovulationDates;
    }

}
