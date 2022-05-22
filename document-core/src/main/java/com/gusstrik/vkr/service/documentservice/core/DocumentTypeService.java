package com.gusstrik.vkr.service.documentservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentTypeDto;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentStateModelFilter;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentTypeFilter;

public interface DocumentTypeService {
    BaseDataResponse<DocumentTypeDto> saveTypeModel(DocumentTypeDto stateModelDto);

    PagingResponseDto<DocumentTypeDto> searchTypeModel(PagingRequestDto<DocumentTypeFilter> requestDto);

    BaseDataResponse<DocumentTypeDto> deleteTypeModel(Long id);
}
