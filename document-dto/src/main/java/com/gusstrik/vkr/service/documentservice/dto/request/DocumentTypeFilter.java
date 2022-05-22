package com.gusstrik.vkr.service.documentservice.dto.request;

import lombok.Data;

@Data
public class DocumentTypeFilter {
    private String query;
    private boolean showDeleted = false;
}
