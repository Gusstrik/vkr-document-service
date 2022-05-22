package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.UserGroupDto;
import com.gusstrik.vkr.service.documentservice.model.UserGroup;

import java.util.HashSet;

public class UserGroupMapper {

   public static UserGroup toModel(UserGroupDto dto){
        UserGroup userGroup = new UserGroup();
        userGroup.setId(dto.getId());
        userGroup.setName(dto.getName());
        userGroup.setUsers(dto.getUsers());
        return userGroup;
    }

    public static UserGroupDto toDto(UserGroup userGroup){
       UserGroupDto userGroupDto = new UserGroupDto();
       userGroupDto.setName(userGroup.getName());
       userGroupDto.setId(userGroup.getId());
       userGroupDto.setUsers(new HashSet<>(userGroup.getUsers()));
       return userGroupDto;
    }
}
