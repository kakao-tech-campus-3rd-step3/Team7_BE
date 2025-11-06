package com.careerfit.global.initializer;

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
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile({"local", "prod"})
@ConditionalOnProperty(name = "kareer-fit.dummy.enabled", havingValue = "true")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberJpaRepository memberRepository;
    private final ApplicationJpaRepository applicationRepository;
    private final CommentRepository commentRepository;
    private Random random = new Random();
    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        System.out.println("====== 개발용 더미 데이터 생성 시작 ======");
//
//        // 1. 프로필 생성
//        List<MentorCareer> career1 = List.of(
//            MentorCareer.of("삼성전자", "Backend 개발자", "2010-01", "2014-12"),
//            MentorCareer.of("Google", "시니어 백엔드 개발자", "2015-01", "현재")
//        );
//
//        List<MentorCareer> career2 = List.of(
//            MentorCareer.of("kakao", "프론트엔드 개발자", "2012-03", "2017-08"),
//            MentorCareer.of("Naver", "프론트엔드 리드", "2017-09", "현재")
//        );
//
//        MentorProfile mentorProfile1 = MentorProfile.of(
//            10,
//            "Google",
//            "시니어 백엔드 개발자",
//            "path/to/cert1.pdf",
//            List.of(MentorCertification.of("정보처리기사"), MentorCertification.of("SQLD")),
//            List.of(
//                MentorEducation.of("서울대학교", "컴퓨터공학과", 2002, 2008),
//                MentorEducation.of("서울대학교", "컴퓨터공학과", 2008, 2010)
//            ),
//            List.of(MentorExpertise.of("Java"), MentorExpertise.of("Spring Boot"),
//                MentorExpertise.of("JPA")),
//            "구글 시니어 개발자입니다.",
//            career1,
//            0.0
//        );
//
//        MentorProfile mentorProfile2 = MentorProfile.of(
//            8,
//            "Naver",
//            "프론트엔드 리드",
//            "path/to/cert2.pdf",
//            List.of(
//                MentorCertification.of("정보처리기사"),
//                MentorCertification.of("빅데이터분석기사"),
//                MentorCertification.of("Adsp"),
//                MentorCertification.of("SQLD")
//            ),
//            List.of(
//                MentorEducation.of("상명대학교", "소프트웨어학과", 2004, 2006),
//                MentorEducation.of("연세대", "소프트웨어학과", 2006, 2010)
//            ),
//            List.of(MentorExpertise.of("React"), MentorExpertise.of("TypeScript")),
//            "네이버 프론트엔드 리드입니다.",
//            career2,
//            0.0
//        );
//
//        MenteeProfile menteeProfile1 = MenteeProfile.of(
//            "서울대학교",
//            "컴퓨터공학과",
//            2026,
//            List.of(MenteeWishCompany.of("카카오"), MenteeWishCompany.of("라인"),
//                MenteeWishCompany.of("네이버")),
//            List.of(MenteeWishPosition.of("백엔드"), MenteeWishPosition.of("서버 개발"))
//        );
//
//        MenteeProfile menteeProfile2 = MenteeProfile.of(
//            "연세대학교",
//            "소프트웨어학과",
//            2027,
//            List.of(MenteeWishCompany.of("쿠팡"), MenteeWishCompany.of("당근")),
//            List.of(MenteeWishPosition.of("iOS"))
//        );
//
//        // 2. 회원 생성
//        // Member를 저장하면 Mento/MenteeProfile도 CascadeType.ALL에 의해 함께 저장
//        Member mento1 = Member.mentor("mento1@naver.com", "010-1111-1111", "홍길동", null,
//            OAuthProvider.KAKAO, "oauth_id_1", mentorProfile1);
//        Member mento2 = Member.mentor("mento2@naver.com", "010-2222-2222", "이순신", null,
//            OAuthProvider.KAKAO, "oauth_id_2", mentorProfile2);
//        Member mentee1 = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null,
//            OAuthProvider.KAKAO, "oauth_id_3", menteeProfile1);
//        Member mentee2 = Member.mentee("mentee2@gmail.com", "010-4444-4444", "박영희", null,
//            OAuthProvider.KAKAO, "oauth_id_4", menteeProfile2);
//        memberRepository.saveAll(List.of(mento1, mento2, mentee1, mentee2));
//
//        // 3. 멘티의 지원서(Application) 생성
//        Application app1 = createApplication("카카오", "2025 신입 백엔드 개발자", mentee1,
//            ApplicationStatus.PREPARING);
//        Application app2 = createApplication("라인", "2025 신입 iOS 개발자", mentee2,
//            ApplicationStatus.APPLIED);
//        applicationRepository.saveAll(List.of(app1, app2));
//
//        // 4. 지원서의 문서 및 자기소개서, 자소서 문항 생성
//        // 4-1. CoverLetterItem 리스트 생성
//        List<CoverLetterItem> items1 = new ArrayList<>(List.of(
//            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "카카오의 기술력에 매료되어...", 1000),
//            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
//        ));
//        List<CoverLetterItem> items2 = new ArrayList<>(List.of(
//            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "카카오의 기술력에 매료되어...", 1000),
//            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
//        ));
//        List<CoverLetterItem> items3 = new ArrayList<>(List.of(
//            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "라인의 기술력에 매료되어...", 1000),
//            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
//        ));
//        List<CoverLetterItem> items4 = new ArrayList<>(List.of(
//            CoverLetterItem.of("지원 동기에 대해 서술하시오.", "라인의 기술력에 매료되어...", 1000),
//            CoverLetterItem.of("협업 경험에 대해 서술하시오.", "팀 프로젝트에서 Git을 활용하여...", 1000)
//        ));
//
//        // 4-2. CoverLetter 생성 및 저장
//        // (Application의 CascadeType.ALL에 의해 CoverLetter 저장, CoverLetter의 CascadeType.ALL에 의해 CoverLetterItem도 함께 저장)
//        CoverLetter cl1 = CoverLetter.createCoverLetter("카카오 신입 백엔드 개발자 자기소개서1", items1);
//        CoverLetter cl2 = CoverLetter.createCoverLetter("카카오 신입 백엔드 개발자 자기소개서2", items2);
//        CoverLetter cl3 = CoverLetter.createCoverLetter("라인 신입 백엔드 개발자 자기소개서1", items3);
//        CoverLetter cl4 = CoverLetter.createCoverLetter("라인 신입 백엔드 개발자 자기소개서2", items4);
//        app1.addDocument(cl1);
//        app1.addDocument(cl2);
//        app2.addDocument(cl3);
//        app2.addDocument(cl4);
//
//        // 5. AttachmentFile 생성 및 저장
//        AttachmentFile resume1 = AttachmentFile.of(
//            "resume1.pdf",
//            "applications/" + app1.getId() + "/resumes/" + UUID.randomUUID()
//                + "_resume1_resume1.pdf",
//            "resume1",
//            app1,
//            AttachmentFileType.RESUME
//        );
//        AttachmentFile portfolio1 = AttachmentFile.of(
//            "portfolio1.pdf",
//            "applications/" + app1.getId() + "/portfolios/" + UUID.randomUUID()
//                + "_portfolio1_portfolio1.pdf",
//            "portfolio1",
//            app1,
//            AttachmentFileType.PORTFOLIO
//        );
//        app1.addDocument(resume1);
//        app1.addDocument(portfolio1);
//
//        AttachmentFile resume2 = AttachmentFile.of(
//            "resume2.pdf",
//            "applications/" + app2.getId() + "/resumes/" + UUID.randomUUID()
//                + "_resume2_resume2.pdf",
//            "resume2",
//            app2,
//            AttachmentFileType.RESUME
//        );
//        AttachmentFile portfolio2 = AttachmentFile.of(
//            "portfolio2.pdf",
//            "applications/" + app2.getId() + "/portfolios/" + UUID.randomUUID()
//                + "_portfolio2_portfolio2.pdf",
//            "portfolio2",
//            app2,
//            AttachmentFileType.PORTFOLIO
//        );
//        app2.addDocument(resume2);
//        app2.addDocument(portfolio2);
//
//        // 6. Comment 생성 및 저장
//        List<Comment> comments = new ArrayList<>();
//        comments.add(Comment.of(cl1, mento1,
//            new CommentCreateRequest("댓글1", new Coordinate(10.0, 10.0, 20.0, 20.0), 1)));
//        comments.add(Comment.of(cl1, mentee1,
//            new CommentCreateRequest("댓글2", new Coordinate(10.0, 10.0, 20.0, 20.0), 2)));
//        comments.add(Comment.of(cl2, mento2,
//            new CommentCreateRequest("댓글3", new Coordinate(15.0, 15.0, 25.0, 25.0), 1)));
//        comments.add(Comment.of(cl2, mentee2,
//            new CommentCreateRequest("댓글4", new Coordinate(15.0, 15.0, 25.0, 25.0), 2)));
//        comments.add(Comment.of(resume1, mento1,
//            new CommentCreateRequest("댓글5", new Coordinate(20.0, 20.0, 30.0, 30.0), 1)));
//        comments.add(Comment.of(resume1, mentee1,
//            new CommentCreateRequest("댓글6", new Coordinate(20.0, 20.0, 30.0, 30.0), 2)));
//        comments.add(Comment.of(portfolio1, mento1,
//            new CommentCreateRequest("댓글7", new Coordinate(25.0, 25.0, 35.0, 35.0), 1)));
//        comments.add(Comment.of(portfolio1, mentee1,
//            new CommentCreateRequest("댓글8", new Coordinate(25.0, 25.0, 35.0, 35.0), 2)));
//        comments.add(Comment.of(resume2, mento2,
//            new CommentCreateRequest("댓글9", new Coordinate(30.0, 30.0, 40.0, 40.0), 1)));
//        comments.add(Comment.of(resume2, mentee2,
//            new CommentCreateRequest("댓글10", new Coordinate(30.0, 30.0, 40.0, 40.0), 2)));
//        comments.add(Comment.of(portfolio2, mento2,
//            new CommentCreateRequest("댓글11", new Coordinate(35.0, 35.0, 45.0, 45.0), 1)));
//        comments.add(Comment.of(portfolio2, mentee2,
//            new CommentCreateRequest("댓글12", new Coordinate(35.0, 35.0, 45.0, 45.0), 2)));
//        commentRepository.saveAll(comments);
//
//        System.out.println("====== 더미 데이터 생성 완료 ======");
        System.out.println("====== 50명의 추가 멘토 생성 시작 ======");
        List<Member> additionalMentors = generateRandomMentors(100);
        memberRepository.saveAll(additionalMentors);
        System.out.println("====== 50명의 추가 멘토 생성 완료 ======");

        System.out.println("====== 더미 데이터 생성 완료 ======");
    }
    private List<Member> generateRandomMentors(int count) {
        List<Member> mentors = new ArrayList<>();

        String[] firstNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안", "송", "홍", "손", "배", "백", "허"};
        String[] lastNames = {"민수", "준호", "영철", "수진", "지은", "혜영", "기훈", "승규", "재훈", "현준", "석진", "태영", "민준", "동욱", "주형", "찬호", "영민", "재호", "세환", "경호", "준영", "윤호", "태준"};

        String[] companies = {
                "삼성전자", "LG", "SK", "현대", "롯데", "GS", "CJ", "포스코", "하이닉스", "카카오", "네이버", "라인",
                "쿠팡", "토스", "당근", "야놀자", "우아한형제들", "직방", "넥슨", "엔씨소프트", "넷마블", "한게임",
                "배달의민족", "배민", "Airbnb Korea", "우버", "어썸", "숨고", "부스트", "메쉬코리아", "티몬", "위메프",
                "11번가", "지마켓", "옥션", "이베이코리아", "쿠팡이츠", "배달팡", "요기요", "스팀",
                "삼성SDI", "삼성생명", "삼성증권", "국민은행", "우리은행", "신한은행", "하나은행", "기업은행", "카카오뱅크", "토스뱅크"
        };

        String[] positions = {
                "주니어 백엔드 개발자", "백엔드 개발자", "시니어 백엔드 개발자", "리드 백엔드 개발자",
                "주니어 프론트엔드 개발자", "프론트엔드 개발자", "시니어 프론트엔드 개발자", "리드 프론트엔드 개발자",
                "안드로이드 개발자", "iOS 개발자", "풀스택 개발자", "데브옵스 엔지니어",
                "머신러닝 엔지니어", "데이터 엔지니어", "데이터 과학자", "AI 개발자",
                "게임 서버 개발자", "게임 클라이언트 개발자", "보안 엔지니어", "QA 엔지니어",
                "솔루션 아키텍트", "시스템 엔지니어", "클라우드 엔지니어", "인프라 엔지니어"
        };

        String[] universities = {
                "서울대학교", "고려대학교", "연세대학교", "성균관대학교", "한양대학교",
                "중앙대학교", "이화여자대학교", "숙명여자대학교", "서강대학교", "홍익대학교",
                "국민대학교", "동국대학교", "명지대학교", "단국대학교", "덕성여자대학교",
                "건국대학교", "경희대학교", "광운대학교", "상명대학교", "세종대학교"
        };

        String[] departments = {
                "컴퓨터공학과", "컴퓨터과학과", "소프트웨어학과", "정보통신공학과",
                "정보보호학과", "인공지능학과", "데이터과학과", "컴퓨터학부",
                "정보시스템학과", "정보기술학과", "전산학과", "시스템소프트웨어학과"
        };

        String[] certifications = {
                "정보처리기사", "정보처리산업기사", "SQLD", "SQLP", "리눅스마스터", "ASIS",
                "빅데이터분석기사", "ADSP", "AWS Solutions Architect", "Google Cloud Professional",
                "Azure Administrator", "Kubernetes", "Docker", "Certified Kubernetes Administrator",
                "CISSP", "CEH", "정보보안기사", "정보시스템감리사"
        };

        String[] skills = {
                "Java", "Python", "JavaScript", "TypeScript", "Go", "Rust", "C++", "Kotlin",
                "Spring Boot", "Spring", "Django", "FastAPI", "Node.js", "Express",
                "React", "Vue.js", "Angular", "Next.js", "Svelte",
                "PostgreSQL", "MySQL", "MongoDB", "Redis", "Elasticsearch",
                "AWS", "GCP", "Azure", "Docker", "Kubernetes", "Terraform",
                "Git", "Linux", "HTTP", "REST API", "GraphQL", "gRPC",
                "JPA", "Hibernate", "MyBatis", "SQLAlchemy", "Prisma",
                "Jenkins", "GitHub Actions", "CircleCI", "GitLab CI"
        };

        String[] descriptions = {
                "대규모 서비스 백엔드 개발 경험이 풍부합니다.",
                "클라우드 네이티브 애플리케이션 설계에 전문가입니다.",
                "마이크로서비스 아키텍처 구축 경험이 있습니다.",
                "높은 트래픽 처리 시스템 최적화 전문가입니다.",
                "데이터베이스 성능 튜닝에 능숙합니다.",
                "DevOps 및 인프라 자동화 전문가입니다.",
                "프론트엔드 성능 최적화 및 사용자 경험 개선에 집중합니다.",
                "모바일 앱 개발 전문가입니다.",
                "보안 아키텍처 설계 경험이 풍부합니다.",
                "AI/ML 모델 서빙 및 배포 경험이 있습니다.",
                "대규모 트래픽 처리 경험이 있습니다.",
                "팀 리더십 경험이 풍부합니다.",
                "스타트업 초기 개발 경험이 있습니다.",
                "엔터프라이즈급 솔루션 개발 경험이 있습니다."
        };

        int oauthIdCounter = 5;

        for (int i = 3; i < count+3; i++) {
            String name = firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)];
            String email = "mento"+i+"@naver.com";
            String phone = generatePhone();

            List<MentorCareer> careers = generateRandomCareers(companies, positions);

            String currentCompany = careers.get(careers.size() - 1).getCompanyName();
            String currentPosition = careers.get(careers.size() - 1).getPosition();

            List<MentorCertification> certs = generateRandomCertifications(certifications);

            List<MentorEducation> educations = generateRandomEducations(universities, departments);

            List<MentorExpertise> expertises = generateRandomSkills(skills);

            int yearsOfExperience = random.nextInt(15) + 1;

            MentorProfile profile = MentorProfile.of(
                    yearsOfExperience,
                    currentCompany,
                    currentPosition,
                    "path/to/cert_" + i + ".pdf",
                    certs,
                    educations,
                    expertises,
                    descriptions[random.nextInt(descriptions.length)],
                    careers,
                    0.0
            );

            Member mentor = Member.mentor(
                    email,
                    phone,
                    name,
                    null,
                    OAuthProvider.KAKAO,
                    "oauth_id_" + oauthIdCounter,
                    profile
            );

            mentors.add(mentor);
            oauthIdCounter++;
        }

        return mentors;
    }


    private String generatePhone() {
        return "010-" + (1000 + random.nextInt(9000)) + "-" + (1000 + random.nextInt(9000));
    }

    private List<MentorCareer> generateRandomCareers(String[] companies, String[] positions) {
        List<MentorCareer> careers = new ArrayList<>();
        int careerCount = random.nextInt(2) + 2;
        int startYear = 2010;

        for (int i = 0; i < careerCount; i++) {
            String company = companies[random.nextInt(companies.length)];
            String position = positions[random.nextInt(positions.length)];

            String startYearMonth = (startYear + (i * 3)) + "-" + String.format("%02d", random.nextInt(12) + 1);
            String endYearMonth = (i == careerCount - 1) ? "현재" : ((startYear + (i * 3) + 2) + "-" + String.format("%02d", random.nextInt(12) + 1));

            careers.add(MentorCareer.of(company, position, startYearMonth, endYearMonth));
        }

        return careers;
    }

    private List<MentorCertification> generateRandomCertifications(String[] certifications) {
        List<MentorCertification> certs = new ArrayList<>();
        int certCount = random.nextInt(3) + 1;
        Set<Integer> usedIndices = new HashSet<>();

        for (int i = 0; i < certCount; i++) {
            int index;
            do {
                index = random.nextInt(certifications.length);
            } while (usedIndices.contains(index));

            usedIndices.add(index);
            certs.add(MentorCertification.of(certifications[index]));
        }

        return certs;
    }

    private List<MentorEducation> generateRandomEducations(String[] universities, String[] departments) {
        List<MentorEducation> educations = new ArrayList<>();
        int eduCount = random.nextInt(2) + 1;

        for (int i = 0; i < eduCount; i++) {
            String university = universities[random.nextInt(universities.length)];
            String department = departments[random.nextInt(departments.length)];
            int startYear = 2002 + (i * 4);
            int endYear = startYear + 4;

            educations.add(MentorEducation.of(university, department, startYear, endYear));
        }

        return educations;
    }

    private List<MentorExpertise> generateRandomSkills(String[] skills) {
        List<MentorExpertise> expertises = new ArrayList<>();
        int skillCount = random.nextInt(3) + 2;
        Set<Integer> usedIndices = new HashSet<>();

        for (int i = 0; i < skillCount; i++) {
            int index;
            do {
                index = random.nextInt(skills.length);
            } while (usedIndices.contains(index));

            usedIndices.add(index);
            expertises.add(MentorExpertise.of(skills[index]));
        }

        return expertises;
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
