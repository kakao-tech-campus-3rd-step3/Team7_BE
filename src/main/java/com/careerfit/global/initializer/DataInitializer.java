package com.careerfit.global.initializer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MenteeProfile;
import com.careerfit.member.domain.MentoProfile;
import com.careerfit.member.repository.MemberJpaRepository;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Component
@Profile({"local","prod"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberJpaRepository memberRepository;
    private final ApplicationJpaRepository applicationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("====== 개발용 더미 데이터 생성 시작 ======");

        // 1. 프로필 생성
        MentoProfile mentoProfile1 = MentoProfile.of(10, "Google", "시니어 백엔드 개발자",
            "path/to/cert1.pdf", "서울대학교", "Java, Spring Boot, JPA", "구글 시니어 개발자입니다.");
        MentoProfile mentoProfile2 = MentoProfile.of(8, "Naver", "프론트엔드 리드",
            "path/to/cert2.pdf", "연세대학교", "React, TypeScript", "네이버 프론트엔드 리드입니다.");
        MenteeProfile menteeProfile1 = MenteeProfile.of("서울대학교", "컴퓨터공학과", 2026, "카카오", "백엔드");
        MenteeProfile menteeProfile2 = MenteeProfile.of("연세대학교", "소프트웨어학과", 2027, "라인", "iOS");

        // 2. 회원 생성
        // Member를 저장하면 Mento/MenteeProfile도 CascadeType.ALL에 의해 함께 저장
        Member mento1 = Member.mento("mento1@naver.com", "010-1111-1111", null, OAuthProvider.KAKAO, "oauth_id_1", mentoProfile1);
        Member mento2 = Member.mento("mento2@naver.com", "010-2222-2222", null, OAuthProvider.KAKAO, "oauth_id_2", mentoProfile2);
        Member mentee1 = Member.mentee("mentee1@gmail.com", "010-3333-3333", null, OAuthProvider.KAKAO, "oauth_id_3", menteeProfile1);
        Member mentee2 = Member.mentee("mentee2@gmail.com", "010-4444-4444", null, OAuthProvider.KAKAO, "oauth_id_4", menteeProfile2);
        memberRepository.saveAll(List.of(mento1, mento2, mentee1, mentee2));

        // 3. 멘티의 지원서(Application) 생성
        Application app1 = createApplication("카카오", "2025 신입 백엔드 개발자", mentee1, ApplicationStatus.PREPARING);
        Application app2 = createApplication("라인", "2025 신입 iOS 개발자", mentee2, ApplicationStatus.APPLIED);
        applicationRepository.saveAll(List.of(app1, app2));

        // 4. 지원서의 문서 및 자기소개서, 자소서 문항 생성
        // 4-1. CoverLetterItem 리스트 생성
        List<CoverLetterItem> items1 = new ArrayList<>(List.of(
            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "카카오의 기술력에 매료되어...", 1000),
            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
        ));
        List<CoverLetterItem> items2 = new ArrayList<>(List.of(
            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "라인의 기술력에 매료되어...", 1000),
            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
        ));

        // 4-2. CoverLetter 생성 및 저장
        // (Application의 CascadeType.ALL에 의해 CoverLetter 저장, CoverLetter의 CascadeType.ALL에 의해 CoverLetterItem도 함께 저장)
        CoverLetter cl1 = CoverLetter.createCoverLetter("카카오 신입 백엔드 개발자 자기소개서1", items1);
        CoverLetter cl2 = CoverLetter.createCoverLetter("라인 신입 백엔드 개발자 자기소개서2", items2);
        app1.addDocument(cl1);
        app2.addDocument(cl2);

        System.out.println("====== 더미 데이터 생성 완료 ======");
    }

    private Application createApplication(String company, String position, Member mentee, ApplicationStatus status) {
        return Application.builder()
            .companyName(company)
            .applyPosition(position)
            .deadLine(LocalDateTime.now().plusDays(10))
            .applicationStatus(status)
            .member(mentee)
            .build();
    }
}
