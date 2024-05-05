package com.project.recyclapp.user.services.implement;

import com.project.commons.Utils;
import com.project.commons.exceptions.CustomException;
import com.project.commons.messages.ErrorMessage;
import com.project.recyclapp.user.models.DocumentType;
import com.project.recyclapp.user.repository.DocumentTypeRepository;
import com.project.recyclapp.user.services.interfaces.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.commons.Utils.noDataValid;

@Service
public class DocumentTypeServiceImplements implements DocumentTypeService {

    @Autowired
    private DocumentTypeRepository repository;

    @Override
    public DocumentType createDocumentType(DocumentType documentType) {
        if (documentType == null) {
            throw noDataValid();
        }
        return repository.save(documentType);
    }

    @Override
    public DocumentType getDocumentTypeById(Integer id) {
        if (id == null) {
            throw noDataValid();
        }
        Optional<DocumentType> documentType = repository.findById(id);
        if (documentType.isEmpty()) {
            throw new CustomException(ErrorMessage.DOCUMENT_TYPE_NO_EXISTS.getMessage());
        }
        return documentType.get();
    }

    @Override
    public DocumentType updateDocumentType(DocumentType newDocumentType, Integer id) {
        if (newDocumentType == null || id == null) {
            throw noDataValid();
        }
        Optional<DocumentType> documentType = repository.findById(id);
        if (documentType.isEmpty()) {
            throw new CustomException(ErrorMessage.DOCUMENT_TYPE_NO_EXISTS.getMessage());
        }
        if (Utils.isNullOrEmptyWithTrim(newDocumentType.getName())){
            documentType.get().setName(newDocumentType.getName());
        }
        return repository.save(documentType.get());
    }

    @Override
    public void deleteDocumentType(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return (List<DocumentType>) repository.findAll();
    }
}
