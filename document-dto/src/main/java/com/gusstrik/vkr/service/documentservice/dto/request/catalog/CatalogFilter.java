package com.gusstrik.vkr.service.documentservice.dto.request.catalog;

import lombok.Data;

import java.util.Set;

@Data
public class CatalogFilter {
    private String query;

    private Long parentId;
}
