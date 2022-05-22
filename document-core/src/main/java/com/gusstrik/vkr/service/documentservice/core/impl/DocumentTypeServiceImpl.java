package com.gusstrik.vkr.service.documentservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.core.DocumentTypeService;
import com.gusstrik.vkr.service.documentservice.core.mapper.DocumentStateModelMapper;
import com.gusstrik.vkr.service.documentservice.core.mapper.DocumentTypeModelMapper;
import com.gusstrik.vkr.service.documentservice.dto.DocumentStateModelDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentTypeDto;
import com.gusstrik.vkr.service.documentservice.dto.request.DocumentTypeFilter;
import com.gusstrik.vkr.service.documentservice.model.DocumentState;
import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import com.gusstrik.vkr.service.documentservice.repository.DocumentStateRepository;
import com.gusstrik.vkr.service.documentservice.repository.DocumentTypeRepository;
import com.gusstrik.vkr.service.documentservice.repository.util.DocumentStateSpecification;
import com.gusstrik.vkr.service.documentservice.repository.util.DocumentTypeSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentTypeServiceImpl implements DocumentTypeService {
    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentStateRepository documentStateRepository;

    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository, DocumentStateRepository documentStateRepository) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentStateRepository = documentStateRepository;
    }

    @Override
    @Transactional
    public BaseDataResponse<DocumentTypeDto> saveTypeModel(DocumentTypeDto typeDto) {
        BaseDataResponse<DocumentTypeDto> response = new BaseDataResponse<>();
        DocumentType documentType = null;
        DocumentState documentState = null;
        if(typeDto.getStateModel()!=null){

            documentState=documentStateRepository.findById(typeDto.getStateModel().getId()).get();
        }
        if (ObjectUtils.isEmpty(typeDto.getId())) {
            documentType = DocumentTypeModelMapper.toModel(typeDto, documentState);
            log.debug("Create new type model with name " + typeDto.getName());
        } else {
            Optional<DocumentType> searchResult = documentTypeRepository.findById(typeDto.getId());
            if (!searchResult.isPresent()) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Типовая модель не найдена");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            documentType = searchResult.get();
            documentType.setState(documentState);
            documentType.setName(typeDto.getName());
            documentType.setDeleted(typeDto.getDeleted());
            log.debug("Updating state model with name " + typeDto.getName());
        }
        DocumentTypeDto result = DocumentTypeModelMapper.toDto(documentTypeRepository.save(documentType));
        response.setSuccess(true);
        response.setData(result);
        return response;
    }

    @Override
    public PagingResponseDto<DocumentTypeDto> searchTypeModel(PagingRequestDto<DocumentTypeFilter> requestDto) {
        if (ObjectUtils.isEmpty(requestDto.getPage()))
            requestDto.setPage(0);
        if (ObjectUtils.isEmpty(requestDto.getLimit()))
            requestDto.setLimit(5);
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());
        Page<DocumentType> page = documentTypeRepository.findAll(DocumentTypeSpecification.specification(requestDto.getData()), pageable);
        PagingResponseDto<DocumentTypeDto> responseDto = new PagingResponseDto();
        responseDto.setTotal(page.getTotalElements());
        responseDto.setPage(page.getNumber());
        responseDto.setSuccess(true);
        responseDto.setLimit(page.getSize());
        if (!CollectionUtils.isEmpty(page.getContent()))
            responseDto.setData(page.get().map(DocumentTypeModelMapper::toDto).collect(Collectors.toList()));
        return responseDto;
    }

    @Override
    public BaseDataResponse<DocumentTypeDto> deleteTypeModel(Long id) {
        Optional<DocumentType> searchResult = documentTypeRepository.findById(id);
        BaseDataResponse<DocumentTypeDto> response = new BaseDataResponse();
        if(!searchResult.isPresent()){
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Типовая модель не найдена");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
        DocumentType documentType = searchResult.get();
        documentType.setDeleted(true);
        response.setData(DocumentTypeModelMapper.toDto(documentTypeRepository.save(documentType)));
        return response;
    }
}
