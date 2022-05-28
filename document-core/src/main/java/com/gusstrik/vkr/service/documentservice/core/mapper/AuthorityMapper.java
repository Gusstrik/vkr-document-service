package com.gusstrik.vkr.service.documentservice.core.mapper;

import com.gusstrik.vkr.service.documentservice.dto.AuthorityDto;
import com.gusstrik.vkr.service.documentservice.model.AuthorityModel;
import com.gusstrik.vkr.service.documentservice.model.UserGroup;
import com.gusstrik.vkr.service.documentservice.repository.AuthorityRepository;
import com.gusstrik.vkr.service.documentservice.repository.CatalogRepository;
import com.gusstrik.vkr.service.documentservice.repository.StoredEntityRepository;
import com.gusstrik.vkr.service.documentservice.repository.UserGroupRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthorityMapper {

    private final UserGroupRepository userGroupRepository;
    private final AuthorityRepository authorityRepository;
    private final CatalogRepository catalogRepository;
    private final StoredEntityRepository storedEntityRepository;

    public AuthorityMapper(UserGroupRepository userGroupRepository, AuthorityRepository authorityRepository, CatalogRepository catalogRepository, StoredEntityRepository storedEntityRepository) {
        this.userGroupRepository = userGroupRepository;
        this.authorityRepository = authorityRepository;
        this.catalogRepository = catalogRepository;
        this.storedEntityRepository = storedEntityRepository;
    }

    @Transactional(readOnly = true)
    public AuthorityModel toModel(AuthorityDto dto) {
        AuthorityModel authorityModel = null;
        if (dto.getId() != null) {
            Optional<AuthorityModel> searchResult = authorityRepository.findById(dto.getId());
            if (searchResult.isPresent())
                authorityModel = searchResult.get();
        }
        if (dto.getId() == null || authorityModel == null) {
            authorityModel = new AuthorityModel();
        }
        authorityModel.setReading(dto.isReading());
        authorityModel.setWriting(dto.isWriting());
        Optional<UserGroup> userGroup = userGroupRepository.findById(dto.getGroupDto().getId());
        authorityModel.setUserGroup(userGroup.orElse(null));
        authorityModel.setStoredEntity(storedEntityRepository.findById(dto.getStoredEntityId()).orElse(null));
        return authorityModel;
    }

    @Transactional(readOnly = true)
    public List<AuthorityModel> toModel(List<AuthorityDto> dtos) {
        Set<Long> authorityIds = dtos.stream().map(AuthorityDto::getId).filter(Objects::nonNull).collect(Collectors.toSet());
        List<AuthorityModel> result = authorityRepository.findAllById(authorityIds);
        if (CollectionUtils.isEmpty(result))
            result = new ArrayList<>();
        result.stream().forEach(auth -> {
            AuthorityDto dto = dtos.stream().filter(a -> auth.getId().equals(a.getId())).findAny().orElse(null);
            auth.setReading(dto.isReading());
            auth.setWriting(dto.isWriting());
        });
        Set<Long> groupIds = dtos.stream().filter(a -> a.getId() == null).map(a -> a.getGroupDto().getId()).collect(Collectors.toSet());
        List<UserGroup> groups = userGroupRepository.findAllById(groupIds);
        dtos.stream()
                .filter(a -> a.getId() == null)
                .map(a -> {
                    Optional<UserGroup> userGroup = groups.stream().filter(g -> g.getId().equals(a.getGroupDto().getId())).findAny();
                    if (!userGroup.isPresent())
                        return null;
                    AuthorityModel authorityModel = new AuthorityModel();
                    authorityModel.setUserGroup(userGroup.get());
                    authorityModel.setReading(a.isReading());
                    authorityModel.setWriting(a.isWriting());
                    return authorityModel;
                })
                .filter(Objects::nonNull)
                .forEach(result::add);
        return result;
    }

    public static AuthorityDto toDto(AuthorityModel authorityModel) {
        AuthorityDto dto = new AuthorityDto();
        dto.setId(authorityModel.getId());
        dto.setReading(authorityModel.getReading());
        dto.setWriting(authorityModel.getWriting());
        dto.setGroupDto(UserGroupMapper.toDto(authorityModel.getUserGroup()));
        if (authorityModel.getStoredEntity() != null)
            dto.setStoredEntityId(authorityModel.getStoredEntity().getId());
        return dto;
    }

}
