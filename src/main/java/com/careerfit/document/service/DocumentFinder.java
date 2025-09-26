package com.careerfit.document.service;

import com.careerfit.document.domain.Document;
import com.careerfit.document.exception.DocumentErrorCode;
import com.careerfit.document.repository.DocumentRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentFinder {
    private final DocumentRepository documentRepository;

    public Document findDocumentOrThrow(Long documentId) {
        return documentRepository.findById(documentId)
            .orElseThrow(() -> new ApplicationException(DocumentErrorCode.DOCUMENT_NOT_FOUND));
    }
}
