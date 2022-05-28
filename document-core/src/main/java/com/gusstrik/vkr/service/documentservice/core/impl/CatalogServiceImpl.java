package com.gusstrik.vkr.service.documentservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.service.documentservice.core.AuthorityService;
import com.gusstrik.vkr.service.documentservice.core.CatalogService;
import com.gusstrik.vkr.service.documentservice.core.config.DocumentServiceCoreConfig;
import com.gusstrik.vkr.service.documentservice.core.mapper.AuthorityMapper;
import com.gusstrik.vkr.service.documentservice.core.mapper.CatalogMapper;
import com.gusstrik.vkr.service.documentservice.dto.StoredEntityDto;
import com.gusstrik.vkr.service.documentservice.dto.catalog.CatalogFullDto;
import com.gusstrik.vkr.service.documentservice.dto.enums.StoredEntityType;
import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import com.gusstrik.vkr.service.documentservice.dto.request.catalog.CatalogCreateRequest;
import com.gusstrik.vkr.service.documentservice.model.Catalog;
import com.gusstrik.vkr.service.documentservice.model.StoredEntity;
import com.gusstrik.vkr.service.documentservice.repository.CatalogRepository;
import com.gusstrik.vkr.service.documentservice.repository.StoredEntityRepository;
import com.gusstrik.vkr.service.documentservice.repository.UserGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    private final UserGroupRepository userGroupRepository;
    private final AuthorityMapper authorityMapper;
    private final AuthorityService authorityService;
    private final StoredEntityRepository storedEntityRepository;

    public CatalogServiceImpl(CatalogRepository catalogRepository, UserGroupRepository userGroupRepository, AuthorityMapper authorityMapper, AuthorityService authorityService, StoredEntityRepository storedEntityRepository) {
        this.catalogRepository = catalogRepository;
        this.userGroupRepository = userGroupRepository;
        this.authorityMapper = authorityMapper;
        this.authorityService = authorityService;
        this.storedEntityRepository = storedEntityRepository;
    }


    @Override
    @Transactional
    public BaseDataResponse<StoredEntityDto> saveCatalog(CatalogCreateRequest catalogCreateRequest) {
        BaseDataResponse<StoredEntityDto> response = new BaseDataResponse<>();
        Catalog parentCatalog = null;
        if (catalogCreateRequest.getParentCatalogId() != null) {
            Optional<Catalog> searchResult = catalogRepository.findById(catalogCreateRequest.getParentCatalogId());
            if (!searchResult.isPresent()) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Родительский каталог не существует");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            parentCatalog = searchResult.get();
        }
        if (catalogCreateRequest.getId() != null) {
            if (!canCreate(catalogCreateRequest.getParentCatalogId())) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Недостаточно прав для создания каталога");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            Optional<Catalog> searchCatalog = catalogRepository.findById(catalogCreateRequest.getId());
            if (searchCatalog.isPresent()) {
                if (!canCreate(catalogCreateRequest.getId())) {
                    response.setSuccess(false);
                    OperationError operationError = new OperationError();
                    operationError.setMessage("Недостаточно прав для редактирования каталога");
                    response.setErrorList(Arrays.asList(operationError));
                    return response;
                }
                Catalog catalog = searchCatalog.get();
                catalog.setParentCatalog(parentCatalog);
                catalog.setName(catalogCreateRequest.getName());
                catalog.setEditor(DocumentServiceCoreConfig.currentUser.get());
                catalog = catalogRepository.save(catalog);
                response.setSuccess(true);
                response.setData(CatalogMapper.toBaseDto(catalog));
            } else {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Каталог не существует");
                response.setErrorList(Arrays.asList(operationError));
            }
        } else {
            Catalog catalog = new Catalog();
            catalog.setName(catalogCreateRequest.getName());
            catalog.setAuthor(DocumentServiceCoreConfig.currentUser.get());
            catalog.setEditor(catalog.getAuthor());
            catalog.setParentCatalog(parentCatalog);
            catalog.setType(StoredEntityType.CATALOG);
            catalog = catalogRepository.save(catalog);
            authorityService.inheritCatalogAuthorities(catalog);
            response.setSuccess(true);
            response.setData(CatalogMapper.toBaseDto(catalog));
        }
        return response;
    }

    @Override
    public BaseDataResponse<CatalogFullDto> loadCatalog(Long id) {
        BaseDataResponse<CatalogFullDto> response = new BaseDataResponse<>();
        Optional<Catalog> searchCatalog = catalogRepository.findById(id);
        if (!searchCatalog.isPresent()) {
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Каталог не существует");
            response.setErrorList(Arrays.asList(operationError));
        }
        Set<UserRights> catalogWrights = authorityService.getUserRights(id);
        List<StoredEntity> storedEntities = storedEntityRepository.loadCatalog(id,DocumentServiceCoreConfig.currentUser.get());
        CatalogFullDto catalogFullDto = CatalogMapper.toFullDto(searchCatalog.get(), catalogWrights, storedEntities);
        response.setSuccess(true);
        response.setData(catalogFullDto);
        return response;
    }

    @Override
    public BaseDataResponse<?> deleteCatalog(Long id) {
        Optional<Catalog> searchCatalog = catalogRepository.findById(id);
        BaseDataResponse response = new BaseDataResponse();
        if (searchCatalog.isPresent()) {
            if (!canCreate(id)) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Недостаточно прав для удаления каталога");
                response.setErrorList(Arrays.asList(operationError));
            }
            storedEntityRepository.deleteInheritance(searchCatalog.get().getId());
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Каталог не существует");
            response.setErrorList(Arrays.asList(operationError));

        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canCreate(Long parentCatalogId) {
        if (parentCatalogId == null)
            return true;
        Optional<Catalog> searchResult = catalogRepository.findById(parentCatalogId);
        if (!searchResult.isPresent())
            return false;
        Catalog catalog = searchResult.get();
        String userName = DocumentServiceCoreConfig.currentUser.get();
        if (ObjectUtils.isEmpty(userName))
            return false;
        if (catalog.getAuthor().equals(userName))
            return true;
        return authorityService.hasWritingAuthority(parentCatalogId);
    }
}
