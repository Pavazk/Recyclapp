package com.project.recyclapp.user.controllers;

import com.project.recyclapp.user.models.DocumentType;
import com.project.recyclapp.user.services.interfaces.DocumentTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("document_type")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;

    @PostMapping
    public DocumentType createDocumentType(@RequestBody @Valid DocumentType documentType) {
        return documentTypeService.createDocumentType(documentType);
    }

    @PutMapping({"/{id}"})
    public DocumentType getDocumentTypeById(@PathVariable Integer id) {
        return documentTypeService.getDocumentTypeById(id);
    }

    @PostMapping({"/{id}"})
    public DocumentType updateDocumentType(@RequestBody DocumentType DocumentType, @PathVariable Integer id) {
        return documentTypeService.updateDocumentType(DocumentType, id);
    }

    @GetMapping
    public List<DocumentType> getAllDocumentType() {
        return documentTypeService.getAllDocumentTypes();
    }


}
