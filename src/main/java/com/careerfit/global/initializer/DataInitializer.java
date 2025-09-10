package com.careerfit.global.initializer;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MenteeProfile;
import com.careerfit.member.domain.MentorCareer;
import com.careerfit.member.domain.MentorProfile;
import com.careerfit.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"local", "prod"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberJpaRepository memberRepository;
    private final ApplicationJpaRepository applicationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("====== 개발용 더미 데이터 생성 시작 ======");

        // 1. 프로필 생성
        List<MentorCareer> career1 = List.of(
                MentorCareer.builder()
                        .companyName("삼성전자")
                        .position("Backend 개발자")
                        .startDate("2010-01")
                        .endDate("2014-12")
                        .build(),
                MentorCareer.builder()
                        .companyName("Google")
                        .position("시니어 백엔드 개발자")
                        .startDate("2015-01")
                        .endDate("현재")
                        .build()
        );

        List<MentorCareer> career2 = List.of(
                MentorCareer.builder()
                        .companyName("kakao")
                        .position("프론트엔드 개발자")
                        .startDate("2012-03")
                        .endDate("2017-08")
                        .build(),
                MentorCareer.builder()
                        .companyName("Naver")
                        .position("프론트엔드 리드")
                        .startDate("2017-09")
                        .endDate("현재")
                        .build()
        );


        MentorProfile mentorProfile1 = MentorProfile.of(10, "Google", "시니어 백엔드 개발자",
                "path/to/cert1.pdf", List.of("정보처리기사", "SQLD"), List.of("서울대학교 컴퓨터공학과 학사 (2002-2008)", "서울대학교 컴퓨터공학과 석사 (2008-2010)"), List.of("Java", "Spring Boot", "JPA"), "구글 시니어 개발자입니다.", career1);
        MentorProfile mentorProfile2 = MentorProfile.of(8, "Naver", "프론트엔드 리드",
                "path/to/cert2.pdf", List.of("정보처리기사", "빅데이터분석기사", "Adsp", "SQLD"), List.of("상명대학교 소프트웨어학과 학사 (2004-2006)", "연세대 소프트웨어학과 학사 (2006-2010)"), List.of("React", "TypeScript"), "네이버 프론트엔드 리드입니다.", career2);
        MenteeProfile menteeProfile1 = MenteeProfile.of("서울대학교", "컴퓨터공학과", 2026, List.of("카카오","라인","네이버"), List.of("백엔드","서버 개발"));
        MenteeProfile menteeProfile2 = MenteeProfile.of("연세대학교", "소프트웨어학과", 2027, List.of("쿠팡","당근"), List.of("iOS"));

        // 2. 회원 생성
        // Member를 저장하면 Mento/MenteeProfile도 CascadeType.ALL에 의해 함께 저장
        Member mento1 = Member.mentor("mento1@naver.com", "010-1111-1111", "홍길동", null, OAuthProvider.KAKAO, "oauth_id_1", mentorProfile1);
        Member mento2 = Member.mentor("mento2@naver.com", "010-2222-2222", "이순신", null, OAuthProvider.KAKAO, "oauth_id_2", mentorProfile2);
        Member mentee1 = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null, OAuthProvider.KAKAO, "oauth_id_3", menteeProfile1);
        Member mentee2 = Member.mentee("mentee2@gmail.com", "010-4444-4444", "박영희", null, OAuthProvider.KAKAO, "oauth_id_4", menteeProfile2);
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
