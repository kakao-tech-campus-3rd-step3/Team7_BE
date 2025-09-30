package com.careerfit.attachmentfile.repository;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long> {
    List<AttachmentFile> findAllByApplicationId(Long applicationId);
}
