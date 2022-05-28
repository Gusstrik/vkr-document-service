package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.StoredEntityDto;
import com.gusstrik.vkr.service.documentservice.dto.catalog.CatalogFullDto;
import com.gusstrik.vkr.service.documentservice.dto.enums.StoredEntityType;
import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import com.gusstrik.vkr.service.documentservice.model.AuthorityModel;
import com.gusstrik.vkr.service.documentservice.model.Catalog;
import com.gusstrik.vkr.service.documentservice.model.StoredEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CatalogMapper {
    public static Catalog toModel(CatalogFullDto fullDto, Catalog parentCatalog, List<AuthorityModel> authorities) {
        Catalog catalog = new Catalog();
        catalog.setName(fullDto.getName());
        catalog.setAuthor(fullDto.getAuthor());
        catalog.setParentCatalog(parentCatalog);
        return catalog;
    }

    public static CatalogFullDto toFullDto(Catalog catalog, Set<UserRights> userRights, List<StoredEntity> storedEntities) {
        CatalogFullDto fullDto = new CatalogFullDto();
        fullDto.setId(catalog.getId());
        fullDto.setName(catalog.getName());
        fullDto.setCreateDate(catalog.getCreateDate());
        fullDto.setAuthor(catalog.getAuthor());
        fullDto.setEditor(catalog.getEditor());
        fullDto.setUpdateDate(catalog.getUpdateDate());
        if (catalog.getParentCatalog() != null)
            fullDto.setParentCatalog(toBaseDto(catalog.getParentCatalog()));
        if(!CollectionUtils.isEmpty(storedEntities))
            fullDto.setStoredEntities(storedEntities.stream().map(StoredEntityMapper::toBaseDto).collect(Collectors.toList()));
        fullDto.setRights(userRights);
        return fullDto;
    }

    public static StoredEntityDto toBaseDto(Catalog catalog) {
        return StoredEntityMapper.toBaseDto(catalog);
    }
}
