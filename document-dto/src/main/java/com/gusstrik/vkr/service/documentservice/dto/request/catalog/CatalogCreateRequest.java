package com.gusstrik.vkr.service.documentservice.dto.request.catalog;

import lombok.Data;

@Data
public class CatalogCreateRequest {

    private Long id;

    private String name;

    private Long parentCatalogId;
}
