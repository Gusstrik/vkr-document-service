package com.gusstrik.vkr.service.documentservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserGroupDto {
    private Long id;
    private String name;
    private Set<String> users;
}
