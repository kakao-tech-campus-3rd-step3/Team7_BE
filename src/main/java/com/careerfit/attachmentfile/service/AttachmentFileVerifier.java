package com.careerfit.attachmentfile.service;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.exception.AttachmentFileErrorCode;
import com.careerfit.global.exception.ApplicationException;

public class AttachmentFileVerifier {

    public static void verifyApplicationOwnership(Long applicationId,
        AttachmentFile attachmentFile) {
        if (!attachmentFile.getApplication().getId().equals(applicationId)) {
            throw new ApplicationException(AttachmentFileErrorCode.ATTACHMENT_FILE_NOT_MATCHED)
                .addErrorInfo("request application Id", applicationId);
        }
    }

}
