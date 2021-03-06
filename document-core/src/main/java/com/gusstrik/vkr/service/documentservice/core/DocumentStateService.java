package com.gusstrik.vkr.service.documentservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentStateModelFilter;

public interface DocumentStateService {
    BaseDataResponse<DocumentStateModelDto> saveStateModel(DocumentStateModelDto stateModelDto);

    PagingResponseDto<DocumentStateModelDto> searchStateModel(PagingRequestDto<DocumentStateModelFilter> requestDto);

    BaseDataResponse<DocumentStateModelDto> deleteStateModel(Long id);
}
