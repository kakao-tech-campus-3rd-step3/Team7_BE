package com.careerfit.attachmentfile.service;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.exception.AttachmentFileErrorCode;
import com.careerfit.attachmentfile.repository.AttachmentFileRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AttachmentFileFinder {

    private final AttachmentFileRepository attachmentFileRepository;

    public AttachmentFile findAttachmentFileOrThrow(Long attachmentFileId) {
        return attachmentFileRepository.findById(attachmentFileId)
            .orElseThrow(() -> new ApplicationException(AttachmentFileErrorCode.ATTACHMENT_FILE_NOT_FOUND));
    }
}
