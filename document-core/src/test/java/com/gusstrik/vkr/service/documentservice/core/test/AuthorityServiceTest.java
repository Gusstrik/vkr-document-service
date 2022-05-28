package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.service.documentservice.core.AuthorityService;
import com.gusstrik.vkr.service.documentservice.dto.AuthorityDto;
import com.gusstrik.vkr.service.documentservice.dto.UserGroupDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityServiceTest extends BaseTest {
    @Autowired
    private AuthorityService authorityService;

    @Test
    public void addAuthorityTest(){
        AuthorityDto authorityDto = new AuthorityDto();
        authorityDto.setWriting(true);
        authorityDto.setReading(true);
        authorityDto.setStoredEntityId(8L);
        UserGroupDto groupDto = new UserGroupDto();
        groupDto.setId(2L);
        authorityDto.setGroupDto(groupDto);
        System.out.println(authorityService.saveAuthority(authorityDto));
    }
}
