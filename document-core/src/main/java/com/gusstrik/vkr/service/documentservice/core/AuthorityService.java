package com.gusstrik.vkr.service.documentservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.service.documentservice.dto.AuthorityDto;
import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import com.gusstrik.vkr.service.documentservice.model.StoredEntity;

import java.util.List;
import java.util.Set;

public interface AuthorityService {
    BaseDataResponse<AuthorityDto> saveAuthority(AuthorityDto authorityDto);

    BaseDataResponse<?> removeAuthority (Long id);

    boolean hasWritingAuthority(Long parentCatalogId);

    Set<UserRights> getUserRights (Long catalogId);

    List<AuthorityDto> inheritCatalogAuthorities(StoredEntity storedEntity);
}
