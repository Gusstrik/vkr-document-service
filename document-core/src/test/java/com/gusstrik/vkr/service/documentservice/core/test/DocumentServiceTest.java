package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.service.documentservice.core.CatalogService;
import com.gusstrik.vkr.service.documentservice.core.DocumentService;
import com.gusstrik.vkr.service.documentservice.core.config.DocumentServiceCoreConfig;
import com.gusstrik.vkr.service.documentservice.dto.DocumentFullDto;
import com.gusstrik.vkr.service.documentservice.dto.DocumentTypeDto;
import com.gusstrik.vkr.service.documentservice.dto.StoredEntityDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;

public class DocumentServiceTest extends BaseTest{
    @Autowired
    private DocumentService documentService;

    @BeforeEach
    public void initUser(){
        DocumentServiceCoreConfig.currentUser.set("user");
    }

    @AfterEach
    public void destroyUser(){
        DocumentServiceCoreConfig.currentUser.remove();
    }

    @Test
    public void createDocumentTest(){
        DocumentFullDto fullDto = new DocumentFullDto();
        fullDto.setName("Документ с наследуемыми правами");
        fullDto.setDescription("Описание документа");
        StoredEntityDto parentCatalog = new StoredEntityDto();
        parentCatalog.setId(8L);
        fullDto.setParentCatalog(parentCatalog);
        fullDto.setFiles(new HashSet<>(Arrays.asList(1L)));
        System.out.println(documentService.saveDocument(fullDto));
    }

    @Test
    public void updateDocumentTest(){
        DocumentFullDto fullDto = new DocumentFullDto();
        fullDto.setId(15L);
        fullDto.setName("Документ изменен");
        fullDto.setDescription("Описание документа");
        StoredEntityDto parentCatalog = new StoredEntityDto();
        parentCatalog.setId(7L);
        DocumentTypeDto typeDto = new DocumentTypeDto();
        typeDto.setId(2L);
        fullDto.setDocumentTypeDto(typeDto);
        fullDto.setDocumentState("Создан");
        fullDto.setParentCatalog(parentCatalog);
        fullDto.setFiles(new HashSet<>(Arrays.asList(1L)));
        System.out.println(documentService.saveDocument(fullDto));
    }

    @Test
    public void loadDocumentTest(){
        System.out.println(documentService.loadDocument(15L));
    }

    @Test
    public void deleteDocumentTest(){
        System.out.println(documentService.deleteDocument(15L));
    }
}
