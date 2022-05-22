package com.gusstrik.vkr.service.documentservice.dto;

import lombok.Data;

@Data
public class DocumentTypeDto {

    private Long id;

    private String name;

    private DocumentStateModelDto stateModel;

    private Boolean deleted;
}
