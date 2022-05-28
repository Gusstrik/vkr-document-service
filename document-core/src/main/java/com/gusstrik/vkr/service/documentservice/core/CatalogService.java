package com.gusstrik.vkr.service.documentservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.service.documentservice.dto.StoredEntityDto;
import com.gusstrik.vkr.service.documentservice.dto.catalog.CatalogFullDto;
import com.gusstrik.vkr.service.documentservice.dto.request.catalog.CatalogCreateRequest;
import com.gusstrik.vkr.service.documentservice.dto.request.catalog.CatalogFilter;

import java.util.List;

public interface CatalogService {

    BaseDataResponse<StoredEntityDto> saveCatalog(CatalogCreateRequest catalogCreateRequest);

    BaseDataResponse<CatalogFullDto> loadCatalog(Long id);

    BaseDataResponse<?> deleteCatalog(Long id);

    boolean canCreate(Long parentCatalogId);
}
