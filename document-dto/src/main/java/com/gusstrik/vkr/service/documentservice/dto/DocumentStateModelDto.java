package com.gusstrik.vkr.service.documentservice.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DocumentStateModelDto {

    private Long id;

    private String name;

    private Set<String> states;

    private Boolean isDeleted=false;
}
