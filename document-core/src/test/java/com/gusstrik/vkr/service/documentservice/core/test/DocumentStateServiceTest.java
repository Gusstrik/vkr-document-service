package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.core.DocumentStateService;
import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentStateModelFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

public class DocumentStateServiceTest extends BaseTest{
    @Autowired
    private DocumentStateService documentStateService;

    @Test
    public void createNewStateModel(){
        DocumentStateModelDto dto = new DocumentStateModelDto();
        dto.setName("Новая модель");
        dto.setStates(new HashSet<>(Arrays.asList("Создан", "Проверен")));
        BaseDataResponse<DocumentStateModelDto> response = documentStateService.saveStateModel(dto);
        System.out.println(response);
    }

    @Test
    public void updateStateModel(){
        DocumentStateModelDto dto = new DocumentStateModelDto();
        dto.setId(1L);
        dto.setName("Измененнная модель");
        dto.setStates(new HashSet<>(Arrays.asList("Создан", "Проверен", "Устарел")));
        BaseDataResponse<DocumentStateModelDto> response = documentStateService.saveStateModel(dto);
        System.out.println(response);
    }

    @Test
    public void searchStateModel(){
        DocumentStateModelFilter filter = new DocumentStateModelFilter();
        filter.setQuery(" изм ");
        filter.setShowDeleted(true);
        PagingRequestDto<DocumentStateModelFilter> requestDto = new PagingRequestDto<>();
        requestDto.setData(filter);
        PagingResponseDto<DocumentStateModelDto> responseDto = documentStateService.searchStateModel(requestDto);
        System.out.println(responseDto);
    }

    @Test
    public void deleteStateModel(){
        System.out.println(documentStateService.deleteStateModel(1L));
    }
}
