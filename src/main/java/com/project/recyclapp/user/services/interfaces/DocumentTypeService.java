package com.project.recyclapp.user.services.interfaces;

import com.project.recyclapp.user.models.DocumentType;

import java.util.List;

public interface DocumentTypeService {

    DocumentType createDocumentType(DocumentType documentType);

    DocumentType getDocumentTypeById(Integer id);

    DocumentType updateDocumentType(DocumentType documentType, Integer id);

    void deleteDocumentType(Integer id);

    List<DocumentType> getAllDocumentTypes();
}
