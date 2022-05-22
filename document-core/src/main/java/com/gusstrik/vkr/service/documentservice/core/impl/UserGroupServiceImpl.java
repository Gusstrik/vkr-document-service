package com.gusstrik.vkr.service.documentservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.core.UserGroupService;
import com.gusstrik.vkr.service.documentservice.core.mapper.DocumentTypeModelMapper;
import com.gusstrik.vkr.service.documentservice.core.mapper.UserGroupMapper;
import com.gusstrik.vkr.service.documentservice.dto.DocumentTypeDto;
import com.gusstrik.vkr.service.documentservice.dto.UserGroupDto;
import com.gusstrik.vkr.service.documentservice.dto.request.UserGroupFilter;
import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import com.gusstrik.vkr.service.documentservice.model.UserGroup;
import com.gusstrik.vkr.service.documentservice.repository.UserGroupRepository;
import com.gusstrik.vkr.service.documentservice.repository.util.DocumentTypeSpecification;
import com.gusstrik.vkr.service.documentservice.repository.util.UserGroupSpecification;
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
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;

    public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    @Transactional
    public BaseDataResponse<UserGroupDto> saveUserGroup(UserGroupDto userGroupDto) {
        UserGroup userGroup;
        BaseDataResponse<UserGroupDto> response = new BaseDataResponse<>();
        if (ObjectUtils.isEmpty(userGroupDto.getId())) {
            userGroup = UserGroupMapper.toModel(userGroupDto);
            log.debug("Creating new group with name " + userGroup.getUsers());
        } else {
            Optional<UserGroup> searchResult = userGroupRepository.findById(userGroupDto.getId());
            if (!searchResult.isPresent()) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Группа пользователей не найдена");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            userGroup = searchResult.get();
            userGroup.setUsers(userGroupDto.getUsers());
            userGroup.setName(userGroupDto.getName());
        }
        userGroup = userGroupRepository.save(userGroup);
        UserGroupDto result = UserGroupMapper.toDto(userGroup);
        response.setSuccess(true);
        response.setData(result);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserGroupDto getById(Long id) {
        Optional<UserGroup> searchResult = userGroupRepository.findById(id);
        if (!searchResult.isPresent())
            return null;
        return UserGroupMapper.toDto(searchResult.get());
    }

    @Override
    @Transactional(readOnly = true)
    public PagingResponseDto<UserGroupDto> searchGroup(PagingRequestDto<UserGroupFilter> requestDto) {
        if (ObjectUtils.isEmpty(requestDto.getPage()))
            requestDto.setPage(0);
        if (ObjectUtils.isEmpty(requestDto.getLimit()))
            requestDto.setLimit(5);
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getLimit());
        Page<UserGroup> page = userGroupRepository.findAll(UserGroupSpecification.specification(requestDto.getData()), pageable);
        PagingResponseDto<UserGroupDto> responseDto = new PagingResponseDto();
        responseDto.setTotal(page.getTotalElements());
        responseDto.setPage(page.getNumber());
        responseDto.setSuccess(true);
        responseDto.setLimit(page.getSize());
        if (!CollectionUtils.isEmpty(page.getContent()))
            responseDto.setData(page.get().map(UserGroupMapper::toDto).collect(Collectors.toList()));
        return responseDto;
    }

    @Override
    @Transactional
    public BaseDataResponse<?> deleteGroup(Long id) {
        Optional<UserGroup> searchResult = userGroupRepository.findById(id);
        BaseDataResponse<DocumentTypeDto> response = new BaseDataResponse();
        if(!searchResult.isPresent()){
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Группа пользователей не найдена");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
        UserGroup userGroup = searchResult.get();
        userGroupRepository.delete(userGroup);
        response.setSuccess(true);
        return response;
    }
}
