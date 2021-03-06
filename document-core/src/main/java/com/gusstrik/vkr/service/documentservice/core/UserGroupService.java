package com.gusstrik.vkr.service.documentservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.documentservice.dto.UserGroupDto;
import com.gusstrik.vkr.service.documentservice.dto.request.UserGroupFilter;

import java.util.List;

public interface UserGroupService {
    BaseDataResponse<UserGroupDto> saveUserGroup(UserGroupDto userGroupDto);

    UserGroupDto getById(Long id);

    List<UserGroupDto> getByUser(String user);

    PagingResponseDto<UserGroupDto> searchGroup(PagingRequestDto<UserGroupFilter> requestDto);

    BaseDataResponse<?> deleteGroup(Long id);
}
