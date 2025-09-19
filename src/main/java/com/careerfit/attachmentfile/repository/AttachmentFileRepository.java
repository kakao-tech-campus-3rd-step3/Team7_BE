package com.careerfit.attachmentfile.repository;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long> {

}
