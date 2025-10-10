package com.careerfit.global.initializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.domain.AttachmentFileType;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.domain.Coordinate;
import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.repository.CommentRepository;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import com.careerfit.member.domain.mentor.MentorCareer;
import com.careerfit.member.domain.mentor.MentorCertification;
import com.careerfit.member.domain.mentor.MentorEducation;
import com.careerfit.member.domain.mentor.MentorExpertise;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.repository.MemberJpaRepository;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Component
@Profile({"local", "prod"})
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberJpaRepository memberRepository;
    private final ApplicationJpaRepository applicationRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("====== 개발용 더미 데이터 생성 시작 ======");

        // 1. 프로필 생성
        List<MentorCareer> career1 = List.of(
            MentorCareer.of("삼성전자", "Backend 개발자", "2010-01", "2014-12"),
            MentorCareer.of("Google", "시니어 백엔드 개발자", "2015-01", "현재")
        );

        List<MentorCareer> career2 = List.of(
            MentorCareer.of("kakao", "프론트엔드 개발자", "2012-03", "2017-08"),
            MentorCareer.of("Naver", "프론트엔드 리드", "2017-09", "현재")
        );

        MentorProfile mentorProfile1 = MentorProfile.of(
            10,
            "Google",
            "시니어 백엔드 개발자",
            "path/to/cert1.pdf",
            List.of(MentorCertification.of("정보처리기사"), MentorCertification.of("SQLD")),
            List.of(
                MentorEducation.of("서울대학교", "컴퓨터공학과", 2002, 2008),
                MentorEducation.of("서울대학교", "컴퓨터공학과", 2008, 2010)
            ),
            List.of(MentorExpertise.of("Java"), MentorExpertise.of("Spring Boot"),
                MentorExpertise.of("JPA")),
            "구글 시니어 개발자입니다.",
            career1,
            0.0
        );

        MentorProfile mentorProfile2 = MentorProfile.of(
            8,
            "Naver",
            "프론트엔드 리드",
            "path/to/cert2.pdf",
            List.of(
                MentorCertification.of("정보처리기사"),
                MentorCertification.of("빅데이터분석기사"),
                MentorCertification.of("Adsp"),
                MentorCertification.of("SQLD")
            ),
            List.of(
                MentorEducation.of("상명대학교", "소프트웨어학과", 2004, 2006),
                MentorEducation.of("연세대", "소프트웨어학과", 2006, 2010)
            ),
            List.of(MentorExpertise.of("React"), MentorExpertise.of("TypeScript")),
            "네이버 프론트엔드 리드입니다.",
            career2,
            0.0
        );

        MenteeProfile menteeProfile1 = MenteeProfile.of(
            "서울대학교",
            "컴퓨터공학과",
            2026,
            List.of(MenteeWishCompany.of("카카오"), MenteeWishCompany.of("라인"),
                MenteeWishCompany.of("네이버")),
            List.of(MenteeWishPosition.of("백엔드"), MenteeWishPosition.of("서버 개발"))
        );

        MenteeProfile menteeProfile2 = MenteeProfile.of(
            "연세대학교",
            "소프트웨어학과",
            2027,
            List.of(MenteeWishCompany.of("쿠팡"), MenteeWishCompany.of("당근")),
            List.of(MenteeWishPosition.of("iOS"))
        );

        // 2. 회원 생성
        // Member를 저장하면 Mento/MenteeProfile도 CascadeType.ALL에 의해 함께 저장
        Member mento1 = Member.mentor("mento1@naver.com", "010-1111-1111", "홍길동", null,
            OAuthProvider.KAKAO, "oauth_id_1", mentorProfile1);
        Member mento2 = Member.mentor("mento2@naver.com", "010-2222-2222", "이순신", null,
            OAuthProvider.KAKAO, "oauth_id_2", mentorProfile2);
        Member mentee1 = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null,
            OAuthProvider.KAKAO, "oauth_id_3", menteeProfile1);
        Member mentee2 = Member.mentee("mentee2@gmail.com", "010-4444-4444", "박영희", null,
            OAuthProvider.KAKAO, "oauth_id_4", menteeProfile2);
        memberRepository.saveAll(List.of(mento1, mento2, mentee1, mentee2));

        // 3. 멘티의 지원서(Application) 생성
        Application app1 = createApplication("카카오", "2025 신입 백엔드 개발자", mentee1,
            ApplicationStatus.PREPARING);
        Application app2 = createApplication("라인", "2025 신입 iOS 개발자", mentee2,
            ApplicationStatus.APPLIED);
        applicationRepository.saveAll(List.of(app1, app2));

        // 4. 지원서의 문서 및 자기소개서, 자소서 문항 생성
        // 4-1. CoverLetterItem 리스트 생성
        List<CoverLetterItem> items1 = new ArrayList<>(List.of(
            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "카카오의 기술력에 매료되어...", 1000),
            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
        ));
        List<CoverLetterItem> items2 = new ArrayList<>(List.of(
            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "카카오의 기술력에 매료되어...", 1000),
            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
        ));
        List<CoverLetterItem> items3 = new ArrayList<>(List.of(
            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "라인의 기술력에 매료되어...", 1000),
            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
        ));
        List<CoverLetterItem> items4 = new ArrayList<>(List.of(
            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "라인의 기술력에 매료되어...", 1000),
            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
        ));

        // 4-2. CoverLetter 생성 및 저장
        // (Application의 CascadeType.ALL에 의해 CoverLetter 저장, CoverLetter의 CascadeType.ALL에 의해 CoverLetterItem도 함께 저장)
        CoverLetter cl1 = CoverLetter.createCoverLetter("카카오 신입 백엔드 개발자 자기소개서1", items1);
        CoverLetter cl2 = CoverLetter.createCoverLetter("카카오 신입 백엔드 개발자 자기소개서2", items2);
        CoverLetter cl3 = CoverLetter.createCoverLetter("라인 신입 백엔드 개발자 자기소개서1", items3);
        CoverLetter cl4 = CoverLetter.createCoverLetter("라인 신입 백엔드 개발자 자기소개서2", items4);
        app1.addDocument(cl1);
        app1.addDocument(cl2);
        app2.addDocument(cl3);
        app2.addDocument(cl4);

        // 5. AttachmentFile 생성 및 저장
        AttachmentFile resume1 = AttachmentFile.of(
            "resume1.pdf",
            "applications/" + app1.getId() + "/resumes/" + UUID.randomUUID()
                + "_resume1_resume1.pdf",
            "resume1",
            app1,
            AttachmentFileType.RESUME
        );
        AttachmentFile portfolio1 = AttachmentFile.of(
            "portfolio1.pdf",
            "applications/" + app1.getId() + "/portfolios/" + UUID.randomUUID()
                + "_portfolio1_portfolio1.pdf",
            "portfolio1",
            app1,
            AttachmentFileType.PORTFOLIO
        );
        app1.addDocument(resume1);
        app1.addDocument(portfolio1);

        AttachmentFile resume2 = AttachmentFile.of(
            "resume2.pdf",
            "applications/" + app2.getId() + "/resumes/" + UUID.randomUUID()
                + "_resume2_resume2.pdf",
            "resume2",
            app2,
            AttachmentFileType.RESUME
        );
        AttachmentFile portfolio2 = AttachmentFile.of(
            "portfolio2.pdf",
            "applications/" + app2.getId() + "/portfolios/" + UUID.randomUUID()
                + "_portfolio2_portfolio2.pdf",
            "portfolio2",
            app2,
            AttachmentFileType.PORTFOLIO
        );
        app2.addDocument(resume2);
        app2.addDocument(portfolio2);

        // 6. Comment 생성 및 저장
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.of(cl1, mento1,
            new CommentCreateRequest("댓글1", new Coordinate(10.0, 10.0, 20.0, 20.0))));
        comments.add(Comment.of(cl1, mentee1,
            new CommentCreateRequest("댓글2", new Coordinate(10.0, 10.0, 20.0, 20.0))));
        comments.add(Comment.of(cl2, mento2,
            new CommentCreateRequest("댓글3", new Coordinate(15.0, 15.0, 25.0, 25.0))));
        comments.add(Comment.of(cl2, mentee2,
            new CommentCreateRequest("댓글4", new Coordinate(15.0, 15.0, 25.0, 25.0))));
        comments.add(Comment.of(resume1, mento1,
            new CommentCreateRequest("댓글5", new Coordinate(20.0, 20.0, 30.0, 30.0))));
        comments.add(Comment.of(resume1, mentee1,
            new CommentCreateRequest("댓글6", new Coordinate(20.0, 20.0, 30.0, 30.0))));
        comments.add(Comment.of(portfolio1, mento1,
            new CommentCreateRequest("댓글7", new Coordinate(25.0, 25.0, 35.0, 35.0))));
        comments.add(Comment.of(portfolio1, mentee1,
            new CommentCreateRequest("댓글8", new Coordinate(25.0, 25.0, 35.0, 35.0))));
        comments.add(Comment.of(resume2, mento2,
            new CommentCreateRequest("댓글9", new Coordinate(30.0, 30.0, 40.0, 40.0))));
        comments.add(Comment.of(resume2, mentee2,
            new CommentCreateRequest("댓글10", new Coordinate(30.0, 30.0, 40.0, 40.0))));
        comments.add(Comment.of(portfolio2, mento2,
            new CommentCreateRequest("댓글11", new Coordinate(35.0, 35.0, 45.0, 45.0))));
        comments.add(Comment.of(portfolio2, mentee2,
            new CommentCreateRequest("댓글12", new Coordinate(35.0, 35.0, 45.0, 45.0))));
        commentRepository.saveAll(comments);

        System.out.println("====== 더미 데이터 생성 완료 ======");
    }

    private Application createApplication(String company, String position, Member mentee,
        ApplicationStatus status) {
        return Application.builder()
            .companyName(company)
            .applyPosition(position)
            .deadLine(LocalDateTime.now().plusDays(10))
            .applicationStatus(status)
            .member(mentee)
            .build();
    }
}
