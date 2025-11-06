package com.careerfit.member.dto.mentor;

import java.time.LocalDate;

public record MentorDashboardItem(
    // 1. 문서 정보
    Long documentId,
    String documentTitle,
    String documentType,

    // 2. 멘티 정보
    Long menteeId,
    String menteeName,

    // 3. 컨텍스트 (지원서) 정보
    Long applicationId,
    String companyName,

    // 4. 멘토링 due date
    // 멘토링 요청 없이 단순 코멘트만 남긴 경우, 아래 필드가 null일 수 있음.
    LocalDate mentoringDueDate
) {

}
