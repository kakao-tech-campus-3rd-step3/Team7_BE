package com.careerfit.document.repository;

import com.careerfit.document.domain.Document;
import com.careerfit.member.dto.mentor.MentorDashboardItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("""
        select new com.careerfit.member.dto.mentor.MentorDashboardItem(
            d.id,
            d.title,
            case
                when d.documentType = com.careerfit.document.domain.DocumentType.COVER_LETTER then '자기소개서'
                when d.documentType = com.careerfit.document.domain.DocumentType.ATTACHMENT_FILE then
                    case
                        when af.attachmentFileType = com.careerfit.attachmentfile.domain.AttachmentFileType.RESUME then '이력서'
                        when af.attachmentFileType = com.careerfit.attachmentfile.domain.AttachmentFileType.PORTFOLIO then '포트폴리오'
                        else '기타 문서'
                    end
                else '기타 문서'
            end,
            mentee.id,
            mentee.name,
            a.id,
            a.companyName,
            (
                select max(m2.dueDate)
                from Mentoring m2
                where m2.documentId = d.id
                  and m2.mento.id   = :mentorId
            )
        )
        from Document d
            join d.application a
            join a.member mentee
            left join AttachmentFile af on af.id = d.id
        where
            exists (select 1 from Mentoring m where m.documentId = d.id and m.mento.id = :mentorId)
            or exists (select 1 from Comment   c where c.document.id = d.id and c.member.id = :mentorId)
        order by coalesce(
                (select cast(max(c.createdDate) as LocalDate) from Comment c where c.document.id = d.id and c.member.id = :mentorId),
                (select max(m.createdDate) from Mentoring m where m.documentId = d.id and m.mento.id = :mentorId)
            ) desc
        """)
    List<MentorDashboardItem> findMentorDashboardItems(@Param("mentorId") Long mentorId);

}
