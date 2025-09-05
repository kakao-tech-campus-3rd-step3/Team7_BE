package com.careerfit.mentoring.domain;

import org.apache.logging.log4j.util.Lazy;

import com.careerfit.application.domain.Application;
import com.careerfit.global.entity.CreatedDateBaseEntity;
import com.careerfit.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentoring")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
