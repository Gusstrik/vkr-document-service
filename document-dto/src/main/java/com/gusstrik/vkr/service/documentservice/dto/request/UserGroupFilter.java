package com.gusstrik.vkr.service.documentservice.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserGroupFilter {
    private String query;
    private Set<String> ids;

}
