package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.model.DocumentState;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class DocumentStateModelMapper {
    public static DocumentStateModelDto toDto(DocumentState documentState) {
        DocumentStateModelDto dto = new DocumentStateModelDto();
        dto.setId(documentState.getId());
        dto.setName(documentState.getName());
        dto.setIsDeleted(documentState.isDeleted());
        if (!ObjectUtils.isEmpty(documentState.getStates()))
            dto.setStates(new HashSet<>(documentState.getStates()));
        return dto;
    }

    public static DocumentState toModel(DocumentStateModelDto dto, DocumentState model) {
        if (model == null)
            model = new DocumentState();
        model.setDeleted(dto.getIsDeleted());
        model.setStates(new ArrayList<>(dto.getStates()));
        model.setName(dto.getName());
        return model;
    }
}
