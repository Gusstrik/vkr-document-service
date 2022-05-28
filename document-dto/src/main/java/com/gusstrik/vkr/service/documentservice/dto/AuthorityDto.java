package com.gusstrik.vkr.service.documentservice.dto;

import lombok.Data;

@Data
public class AuthorityDto {

    private Long id;

    private UserGroupDto groupDto;

    private boolean reading;

    private boolean writing;

    private Long storedEntityId;
}
