package com.careerfit.mentoring.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.global.entity.CreatedDateBaseEntity;
import com.careerfit.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mentoring")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Mentoring extends CreatedDateBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private MentoringStatus mentoringStatus;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mento_id")
    private Member mento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_id")
    private Member mentee;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long documentId;

    @Column(nullable = false)
    private LocalDate dueDate;
    public static Mentoring of(
        Application application,
        Member mento,
        Member mentee,
        String title,
        String description,
        Long documentId,
        LocalDate dueDate
    ) {
        return Mentoring.builder()
            .application(application)
            .mento(mento)
            .mentee(mentee)
            .title(title)
            .description(description)
            .documentId(documentId)
            .dueDate(dueDate)
            .mentoringStatus(MentoringStatus.PLAN_TO_APPLY)
            .build();
    }
}
