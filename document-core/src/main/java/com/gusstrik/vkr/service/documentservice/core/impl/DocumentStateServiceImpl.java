package com.gusstrik.vkr.service.documentservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.core.DocumentStateService;
import com.gusstrik.vkr.service.documentservice.core.mapper.DocumentStateModelMapper;
import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentStateModelFilter;
import com.gusstrik.vkr.service.documentservice.model.DocumentState;
import com.gusstrik.vkr.service.documentservice.repository.DocumentStateRepository;
import com.gusstrik.vkr.service.documentservice.repository.util.DocumentStateSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentStateServiceImpl implements DocumentStateService {
    private final DocumentStateRepository documentStateRepository;

    public DocumentStateServiceImpl(DocumentStateRepository documentStateRepository) {
        this.documentStateRepository = documentStateRepository;
    }

    @Transactional
    @Override
    public BaseDataResponse<DocumentStateModelDto> saveStateModel(DocumentStateModelDto stateModelDto) {
        BaseDataResponse<DocumentStateModelDto> response = new BaseDataResponse<>();
        DocumentState documentState = null;
        if (ObjectUtils.isEmpty(stateModelDto.getId())) {
            log.debug("Create new state model with name " + stateModelDto.getName());
        } else {
            Optional<DocumentState> searchResult = documentStateRepository.findById(stateModelDto.getId());
            if (!searchResult.isPresent()) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Статусная модель не найдена");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            documentState = searchResult.get();
            log.debug("Updating state model with name " + stateModelDto.getName());
        }
        documentState = DocumentStateModelMapper.toModel(stateModelDto, documentState);
        DocumentStateModelDto result = DocumentStateModelMapper.toDto(documentStateRepository.save(documentState));
        response.setSuccess(true);
        response.setData(result);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PagingResponseDto<DocumentStateModelDto> searchStateModel(PagingRequestDto<DocumentStateModelFilter> requestDto) {
        if (ObjectUtils.isEmpty(requestDto.getPage()))
            requestDto.setPage(0);
        if (ObjectUtils.isEmpty(requestDto.getLimit()))
            requestDto.setLimit(5);
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());
        Page<DocumentState> page = documentStateRepository.findAll(DocumentStateSpecification.specification(requestDto.getData()), pageable);
        PagingResponseDto<DocumentStateModelDto> responseDto = new PagingResponseDto();
        responseDto.setTotal(page.getTotalElements());
        responseDto.setPage(page.getNumber());
        responseDto.setSuccess(true);
        responseDto.setLimit(page.getSize());
        if (!CollectionUtils.isEmpty(page.getContent()))
            responseDto.setData(page.get().map(DocumentStateModelMapper::toDto).collect(Collectors.toList()));
        return responseDto;
    }

    @Override
    @Transactional
    public BaseDataResponse<DocumentStateModelDto> deleteStateModel(Long id) {
        Optional<DocumentState> searchResult = documentStateRepository.findById(id);
        BaseDataResponse<DocumentStateModelDto> response = new BaseDataResponse();
        if(!searchResult.isPresent()){
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Статусная модель не найдена");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
        DocumentState documentState = searchResult.get();
        documentState.setDeleted(true);
        response.setData(DocumentStateModelMapper.toDto(documentStateRepository.save(documentState)));
        return response;
    }
}
