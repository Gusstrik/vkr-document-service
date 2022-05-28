package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.StoredEntityDto;
import com.gusstrik.vkr.service.documentservice.model.StoredEntity;

public class StoredEntityMapper {
    public static StoredEntityDto toBaseDto(StoredEntity storedEntity) {
        StoredEntityDto baseDto = new StoredEntityDto();
        baseDto.setId(storedEntity.getId());
        baseDto.setName(storedEntity.getName());
        baseDto.setAuthor(storedEntity.getAuthor());
        baseDto.setCreateDate(storedEntity.getCreateDate());
        baseDto.setEditor(storedEntity.getEditor());
        baseDto.setUpdateDate(storedEntity.getUpdateDate());
        baseDto.setStoredEntityType(storedEntity.getType());
        return baseDto;
    }
}
