package com.gusstrik.vkr.service.documentservice.dto;

import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
public class DocumentFullDto extends StoredEntityDto {
    private Collection<UserRights> rights;

    private DocumentTypeDto documentTypeDto;

    private String documentState;

    private Set<Long> files;

    private String description;

    private StoredEntityDto parentCatalog;
}
