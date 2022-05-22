package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentTypeDto;
import com.gusstrik.vkr.service.documentservice.model.DocumentState;
import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class DocumentTypeModelMapper {
    public static DocumentTypeDto toDto(DocumentType documentType) {
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setId(documentType.getId());
        dto.setName(documentType.getName());
        dto.setDeleted(documentType.getDeleted());
        if (documentType.getState() != null)
            dto.setStateModel(DocumentStateModelMapper.toDto(documentType.getState()));
        return dto;
    }

    public static DocumentType toModel(DocumentTypeDto dto, DocumentState model) {
        DocumentType documentType = new DocumentType();
        documentType.setDeleted(dto.getDeleted());
        documentType.setState(model);
        documentType.setName(dto.getName());
        return documentType;
    }
}
