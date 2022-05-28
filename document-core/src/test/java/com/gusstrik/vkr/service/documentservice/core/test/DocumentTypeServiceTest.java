package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.core.DocumentStateService;
import com.gusstrik.vkr.service.documentservice.core.DocumentTypeService;
import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentTypeDto;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentStateModelFilter;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentTypeFilter;
import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

public class DocumentTypeServiceTest extends BaseTest {
    @Autowired
    private DocumentTypeService documentTypeService;

    @Test
    public void createNewTypeModel(){
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setName("Новый тип");
        dto.setDeleted(false);
        DocumentStateModelDto modelDto = new DocumentStateModelDto();
        modelDto.setId(2L);
        dto.setStateModel(modelDto);
        BaseDataResponse<DocumentTypeDto> response = documentTypeService.saveTypeModel(dto);
        System.out.println(response);
    }

    @Test
    public void updateTypeModel(){
        DocumentTypeDto dto = new DocumentTypeDto();
        dto.setId(1L);
        dto.setName("Измененный тип");
        DocumentStateModelDto modelDto = new DocumentStateModelDto();
        modelDto.setId(1L);
        dto.setStateModel(null);
        BaseDataResponse<DocumentTypeDto> response = documentTypeService.saveTypeModel(dto);
        System.out.println(response);
    }

    @Test
    public void searchTypeModel(){
        DocumentTypeFilter filter = new DocumentTypeFilter();
        filter.setQuery(" иfзм ");
        filter.setShowDeleted(true);
        PagingRequestDto<DocumentTypeFilter> requestDto = new PagingRequestDto<>();
        requestDto.setData(filter);
        PagingResponseDto<DocumentTypeDto> responseDto = documentTypeService.searchTypeModel(requestDto);
        System.out.println(responseDto);
    }

    @Test
    public void deleteStateModel(){
        System.out.println(documentTypeService.deleteTypeModel(1L));
    }
}
