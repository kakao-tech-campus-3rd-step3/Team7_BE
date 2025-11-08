package com.careerfit.application.domain;

import com.careerfit.application.dto.ApplicationContentUpdateRequest;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.document.domain.Document;
import com.careerfit.global.entity.TimeBaseEntity;
import com.careerfit.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Application extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String applyPosition;

    private LocalDateTime deadLine;

    private String location;

    private String employmentType;

    private Integer careerRequirement;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    public static Application of(ApplicationRegisterRequest request, Member member) {
        return Application.builder()
            .companyName(request.companyName())
            .applyPosition(request.applyPosition())
            .deadLine(request.deadline())
            .location(request.location())
            .employmentType(request.employmentType())
            .careerRequirement(request.careerRequirement())
            .url(request.url())
            .applicationStatus(ApplicationStatus.PREPARING) // 기본 상태값 설정
            .member(member)
            .build();
    }

    public void addDocument(Document document) {
        documents.add(document);
        document.setApplication(this);
    }

    public void updateStatus(ApplicationStatus newStatus) {
        this.applicationStatus = newStatus;
    }

    public void updateContent(ApplicationContentUpdateRequest request) {
        this.companyName = request.companyName();
        this.applyPosition = request.applyPosition();
        this.deadLine = request.deadline();
        this.location = request.location();
        this.employmentType = request.employmentType();
        this.careerRequirement = request.careerRequirement();
    }
}
