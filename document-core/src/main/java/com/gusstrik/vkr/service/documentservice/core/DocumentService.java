package com.gusstrik.vkr.service.documentservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.service.documentservice.dto.DocumentFullDto;

public interface DocumentService {
    BaseDataResponse<DocumentFullDto> saveDocument(DocumentFullDto documentFullDto);

    BaseDataResponse<DocumentFullDto> loadDocument(Long id);

    BaseDataResponse<?> deleteDocument(Long id);
}
