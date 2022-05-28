package com.gusstrik.vkr.service.documentservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.service.documentservice.core.AuthorityService;
import com.gusstrik.vkr.service.documentservice.core.config.DocumentServiceCoreConfig;
import com.gusstrik.vkr.service.documentservice.core.mapper.AuthorityMapper;
import com.gusstrik.vkr.service.documentservice.dto.AuthorityDto;
import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import com.gusstrik.vkr.service.documentservice.model.AuthorityModel;
import com.gusstrik.vkr.service.documentservice.model.StoredEntity;
import com.gusstrik.vkr.service.documentservice.repository.AuthorityRepository;
import com.gusstrik.vkr.service.documentservice.repository.CatalogRepository;
import com.gusstrik.vkr.service.documentservice.repository.StoredEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;
    private final CatalogRepository catalogRepository;
    private final StoredEntityRepository storedEntityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper, CatalogRepository catalogRepository, StoredEntityRepository storedEntityRepository) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
        this.catalogRepository = catalogRepository;
        this.storedEntityRepository = storedEntityRepository;
    }

    @Override
    @Transactional
    public BaseDataResponse<AuthorityDto> saveAuthority(AuthorityDto authorityDto) {
        AuthorityModel model;
        if (authorityDto.getId() == null)
            model = authorityMapper.toModel(authorityDto);
        else {
            model = authorityRepository.getById(authorityDto.getId());
            model.setWriting(authorityDto.isWriting());
            model.setReading(authorityDto.isReading());
        }
        BaseDataResponse<AuthorityDto> response = new BaseDataResponse();
        response.setSuccess(true);
        response.setData(AuthorityMapper.toDto(authorityRepository.save(model)));
        return response;
    }

    @Override
    public BaseDataResponse<?> removeAuthority(Long id) {
        BaseDataResponse response = new BaseDataResponse();
        response.setSuccess(true);
        Optional<AuthorityModel> authorityModel = authorityRepository.findById(id);
        authorityModel.ifPresent(authorityRepository::delete);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasWritingAuthority(Long parentCatalogId) {
        if (parentCatalogId == null)
            return true;
        Optional<AuthorityModel> authorityModel = authorityRepository.findWritingAuthority(DocumentServiceCoreConfig.currentUser.get(), parentCatalogId);
        return authorityModel.isPresent();
    }

    @Override
    public Set<UserRights> getUserRights(Long storedEntityId) {
        Set<UserRights> userRights = new HashSet<>();
        List<AuthorityModel> authoritiesModelList = authorityRepository.findByStoredEntityId(storedEntityId);
        if (CollectionUtils.isEmpty(authoritiesModelList)) {
            userRights.add(UserRights.WRITING);
            userRights.add(UserRights.READING);
        } else {
            authoritiesModelList = authorityRepository.findUserAuthorities(storedEntityId, DocumentServiceCoreConfig.currentUser.get());
            if (!CollectionUtils.isEmpty(authoritiesModelList))
                authoritiesModelList.forEach(a -> {
                    if (a.getWriting())
                        userRights.add(UserRights.WRITING);
                    if (a.getReading())
                        userRights.add(UserRights.READING);
                });
        }
        return userRights;

    }


    @Override
    public List<AuthorityDto> inheritCatalogAuthorities(StoredEntity storedEntity) {
        if (storedEntity.getParentCatalog() == null)
            return new ArrayList<>();
        List<AuthorityModel> parentAuthorities = authorityRepository.findByStoredEntityId(storedEntity.getParentCatalog().getId());
        List<AuthorityModel> authorities = parentAuthorities.stream().map(pA -> {
            AuthorityModel authorityModel = new AuthorityModel();
            authorityModel.setStoredEntity(storedEntity);
            authorityModel.setWriting(pA.getWriting());
            authorityModel.setReading(pA.getReading());
            authorityModel.setUserGroup(pA.getUserGroup());
            return authorityModel;
        }).collect(Collectors.toList());
        authorities = authorityRepository.saveAll(authorities);
        return authorities.stream().map(AuthorityMapper::toDto).collect(Collectors.toList());
    }
}
