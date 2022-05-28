package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.service.documentservice.core.UserGroupService;
import com.gusstrik.vkr.service.documentservice.dto.UserGroupDto;
import com.gusstrik.vkr.service.documentservice.dto.request.UserGroupFilter;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

public class UserGroupServiceTest extends BaseTest{
    @Autowired
    UserGroupService userGroupService;

    @Test
    public void createNewGroupTest(){
        UserGroupDto userGroupDto = new UserGroupDto();
        userGroupDto.setUsers(new HashSet<>(Arrays.asList("user")));
        userGroupDto.setName("test_group");
        System.out.println(userGroupService.saveUserGroup(userGroupDto));
    }

    @Test
    public void updateGroupTest(){
        UserGroupDto userGroupDto = new UserGroupDto();
        userGroupDto.setId(1L);
        userGroupDto.setUsers(new HashSet<>(Arrays.asList("test_user","user2")));
        userGroupDto.setName("test_edit_group");
        System.out.println(userGroupService.saveUserGroup(userGroupDto));
    }

    @Test
    public void getUserGroupTest(){
        System.out.println(userGroupService.getById(1L));
    }

    @Test
    public void searchUserGroupTest(){
        UserGroupFilter filter = new UserGroupFilter();
        filter.setQuery(" ed ");
        PagingRequestDto<UserGroupFilter> requestDto = new PagingRequestDto<>();
        requestDto.setData(filter);
        System.out.println(userGroupService.searchGroup(requestDto));
    }

    @Test
    public void searchUserGroupByUserTest(){
        UserGroupFilter filter = new UserGroupFilter();
        filter.setUser("user");
        PagingRequestDto<UserGroupFilter> requestDto = new PagingRequestDto<>();
        requestDto.setData(filter);
        System.out.println(userGroupService.searchGroup(requestDto));
    }

    @Test
    public void deleteUserGroup(){
        System.out.println(userGroupService.deleteGroup(1L));
    }
}
