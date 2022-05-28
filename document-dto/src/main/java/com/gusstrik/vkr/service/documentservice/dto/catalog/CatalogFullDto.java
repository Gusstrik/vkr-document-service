package com.gusstrik.vkr.service.documentservice.dto.catalog;

import com.gusstrik.vkr.service.documentservice.dto.StoredEntityDto;
import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class CatalogFullDto extends StoredEntityDto {

    private StoredEntityDto parentCatalog;

    private Collection<UserRights> rights;

    private List<StoredEntityDto> storedEntities;


}
