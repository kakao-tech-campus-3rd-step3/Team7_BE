package com.careerfit.application.domain;

import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.document.domain.Document;
import com.careerfit.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Application {

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
                .applicationStatus(ApplicationStatus.PREPARING) // 기본 상태값 설정
                .member(member)
                .build();
    }

    public void addDocument(Document document) {
        documents.add(document);
        document.setApplication(this);
    }
}
