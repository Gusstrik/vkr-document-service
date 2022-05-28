package com.gusstrik.vkr.service.documentservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.service.documentservice.core.AuthorityService;
import com.gusstrik.vkr.service.documentservice.core.DocumentService;
import com.gusstrik.vkr.service.documentservice.core.config.DocumentServiceCoreConfig;
import com.gusstrik.vkr.service.documentservice.core.mapper.DocumentMapper;
import com.gusstrik.vkr.service.documentservice.dto.DocumentFullDto;
import com.gusstrik.vkr.service.documentservice.dto.enums.StoredEntityType;
import com.gusstrik.vkr.service.documentservice.dto.enums.UserRights;
import com.gusstrik.vkr.service.documentservice.model.Catalog;
import com.gusstrik.vkr.service.documentservice.model.Document;
import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import com.gusstrik.vkr.service.documentservice.repository.CatalogRepository;
import com.gusstrik.vkr.service.documentservice.repository.DocumentRepository;
import com.gusstrik.vkr.service.documentservice.repository.DocumentTypeRepository;
import com.gusstrik.vkr.service.documentservice.repository.StoredEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentTypeRepository documentTypeRepository;
    private final CatalogRepository catalogRepository;
    private final AuthorityService authorityService;
    private final DocumentRepository documentRepository;
    private final StoredEntityRepository storedEntityRepository;

    public DocumentServiceImpl(DocumentTypeRepository documentTypeRepository, CatalogRepository catalogRepository, AuthorityService authorityService, DocumentRepository documentRepository, StoredEntityRepository storedEntityRepository) {
        this.documentTypeRepository = documentTypeRepository;
        this.catalogRepository = catalogRepository;
        this.authorityService = authorityService;
        this.documentRepository = documentRepository;
        this.storedEntityRepository = storedEntityRepository;
    }


    @Override
    @Transactional
    public BaseDataResponse<DocumentFullDto> saveDocument(DocumentFullDto documentFullDto) {
        Document document;
        BaseDataResponse<DocumentFullDto> response = new BaseDataResponse<>();
        if (!authorityService.hasWritingAuthority(documentFullDto.getParentCatalog() == null ? null : documentFullDto.getParentCatalog().getId())) {
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Недостаточно прав для сохранения в каталоге");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
        if (documentFullDto.getId() == null) {
            document = new Document();
            document.setAuthor(DocumentServiceCoreConfig.currentUser.get());
        } else {
            document = documentRepository.findById(documentFullDto.getId()).orElse(null);
            if (document == null) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Документ не найден");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            Set<UserRights> userRightsSet = authorityService.getUserRights(documentFullDto.getId());
            if (!userRightsSet.contains(UserRights.WRITING)) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Недостаточно прав для редактирования документа");
                response.setErrorList(Arrays.asList(operationError));
                return response;
            }
            document.setEditor(DocumentServiceCoreConfig.currentUser.get());
        }
        document.setEditor(DocumentServiceCoreConfig.currentUser.get());
        document.setName(documentFullDto.getName());
        document.setDescription(documentFullDto.getDescription());
        document.setFilesId(documentFullDto.getFiles());
        document.setType(StoredEntityType.DOCUMENT);
        document.setState(documentFullDto.getDocumentState());
        if (documentFullDto.getDocumentTypeDto() != null && documentFullDto.getDocumentTypeDto().getId() != null) {
            DocumentType documentType = documentTypeRepository.findById(documentFullDto.getDocumentTypeDto().getId()).orElse(null);
            document.setDocumentType(documentType);
        }
        if (documentFullDto.getParentCatalog() != null && documentFullDto.getParentCatalog().getId() != null) {
            Catalog catalog = catalogRepository.findById(documentFullDto.getParentCatalog().getId()).orElse(null);
            document.setParentCatalog(catalog);
        }
        document = documentRepository.save(document);
        if (documentFullDto.getId() == null)
            authorityService.inheritCatalogAuthorities(document);
        response.setSuccess(true);
        DocumentFullDto result = DocumentMapper.toFullDto(document);
        result.setRights(authorityService.getUserRights(document.getId()));
        response.setData(result);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseDataResponse<DocumentFullDto> loadDocument(Long id) {
        Document document = documentRepository.findById(id).orElse(null);
        BaseDataResponse<DocumentFullDto> response = new BaseDataResponse<>();
        if(document==null){
            response.setSuccess(false);
            OperationError error = new OperationError();
            error.setMessage("Документ не существует");
            response.setErrorList(Arrays.asList(error));
            return response;
        }
        Set<UserRights> userRights = authorityService.getUserRights(id);
        if(!userRights.contains(UserRights.READING)){
            response.setSuccess(false);
            OperationError error = new OperationError();
            error.setMessage("Недостаточно прав для просмотра");
            response.setErrorList(Arrays.asList(error));
            return response;
        }
        response.setSuccess(true);
        DocumentFullDto result = DocumentMapper.toFullDto(document);
        result.setRights(userRights);
        response.setData(result);
        return response;
    }

    @Override
    public BaseDataResponse<?> deleteDocument(Long id) {
        Optional<Document> searchDocument = documentRepository.findById(id);
        BaseDataResponse response = new BaseDataResponse();
        if (searchDocument.isPresent()) {
            if (!authorityService.hasWritingAuthority(id)) {
                response.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage("Недостаточно прав для удаления документа");
                response.setErrorList(Arrays.asList(operationError));
            }
            authorityService.deleteEntityAuthorities(id);
            documentRepository.delete(searchDocument.get());
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Документ не существует");
            response.setErrorList(Arrays.asList(operationError));
        }
        return response;
    }
}
