package com.careerfit.attachmentfile.repository;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.document.domain.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long> {
    Page<AttachmentFile> findAllByApplicationIdAndAttachmentFileType(Long applicationId,
        DocumentType attachmentFileType, Pageable pageable);
}
