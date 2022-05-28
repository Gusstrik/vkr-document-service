package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.DocumentFullDto;
import com.gusstrik.vkr.service.documentservice.model.Document;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DocumentMapper {

    public static DocumentFullDto toFullDto(Document document){
        DocumentFullDto dto = new DocumentFullDto();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setDescription(document.getDescription());
        dto.setAuthor(document.getAuthor());
        dto.setEditor(document.getEditor());
        dto.setCreateDate(document.getCreateDate());
        dto.setUpdateDate(document.getUpdateDate());
        dto.setFiles(new HashSet<>(document.getFilesId()));
        dto.setDocumentState(document.getState());
        if(document.getDocumentType()!=null){
            dto.setDocumentTypeDto(DocumentTypeModelMapper.toDto(document.getDocumentType()));
        }
        if(document.getParentCatalog()!=null){
            dto.setParentCatalog(StoredEntityMapper.toBaseDto(document.getParentCatalog()));
        }
        return dto;
    }
}
