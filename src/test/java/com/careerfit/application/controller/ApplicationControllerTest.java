package com.careerfit.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.service.ApplicationCommandService;
import com.careerfit.application.service.ApplicationQueryService;
import com.careerfit.common.support.ControllerTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// ControllerTestSupport는 시큐리티 설정 등 컨트롤러 테스트에 공통적으로 필요한 설정을 모아둔 클래스일 수 있습니다.
// 만약 없다면 @WebMvcTest(ApplicationController.class) 로 변경하고 시큐리티 설정을 추가해야 합니다.
class ApplicationControllerTest extends ControllerTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // ApplicationCommandService를 가짜 객체로 컨테이너에 등록
    private ApplicationCommandService applicationCommandService;

    @MockBean // ApplicationQueryService도 사용될 수 있으므로 MockBean으로 등록
    private ApplicationQueryService applicationQueryService;

    @Test
    @DisplayName("신규 지원서를 성공적으로 등록하고 201 Created 상태 코드를 반환한다")
        // @WithMockCustomUser // 실제 프로젝트의 인증 방식을 테스트하기 위한 어노테이션
    void registerApplication_Success() throws Exception {
        // given: 이런 데이터가 주어졌을 때
        ApplicationRegisterRequest request = new ApplicationRegisterRequest(
                "테스트 컴퍼니", "서버 개발자", LocalDateTime.now().plusDays(30),
                "서울시 강남구", "정규직", 3, "https://example.com/job/123"
        );

        // 서비스의 registerApplication 메소드는 void이므로, 아무것도 하지 않도록 설정
        // memberId가 1L인 사용자가 요청했다고 가정
        doNothing().when(applicationCommandService)
                .registerApplication(any(ApplicationRegisterRequest.class), eq(1L));

        // when & then: 이 API를 호출하면, 이런 결과가 나와야 한다
        mockMvc.perform(post("/api/v1/applications") // POST 요청을 이 URL로 보낸다
                                .contentType(MediaType.APPLICATION_JSON) // 요청 본문의 타입은 JSON
                                .content(objectMapper.writeValueAsString(request))
                        // request 객체를 JSON 문자열로 변환하여 본문에 담는다
                )
                .andDo(print()) // 요청/응답 전체 과정을 콘솔에 출력
                .andExpect(status().isCreated()); // HTTP 상태 코드가 201 Created 인지 검증

        // verify: 컨트롤러가 정확한 인자로 서비스 메소드를 호출했는지 검증
        verify(applicationCommandService).registerApplication(any(ApplicationRegisterRequest.class),
                eq(1L));
    }
}