package com.careerfit.application.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApplicationCommandService 단위 테스트")
class ApplicationCommandServiceTest {

    @InjectMocks
    private ApplicationCommandService applicationCommandService;

    @Mock
    private ApplicationJpaRepository applicationJpaRepository;

    @Mock
    private MemberFinder memberFinder;

    @Test
    @DisplayName("registerApplication 메소드는 회원 정보를 찾아 지원서를 성공적으로 저장한다")
    void register_Success() {
        // given
        Long memberId = 1L;
        ApplicationRegisterRequest request = new ApplicationRegisterRequest(
                "테스트 컴퍼니", "서버 개발자", LocalDateTime.now().plusDays(30),
                "서울시 강남구", "정규직", 3, "https://example.com/job/123"
        );
        Member mockMember = Member.builder().id(memberId).build();

        given(memberFinder.getMemberOrThrow(memberId)).willReturn(mockMember);
        given(applicationJpaRepository.save(any(Application.class))).willReturn(null);

        // when:
        applicationCommandService.register(request, memberId);

        // then:
        verify(memberFinder).getMemberOrThrow(memberId);
        verify(applicationJpaRepository).save(any(Application.class));
    }
}