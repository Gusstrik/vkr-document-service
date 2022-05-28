package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.service.documentservice.core.CatalogService;
import com.gusstrik.vkr.service.documentservice.core.config.DocumentServiceCoreConfig;
import com.gusstrik.vkr.service.documentservice.dto.AuthorityDto;
import com.gusstrik.vkr.service.documentservice.dto.UserGroupDto;
import com.gusstrik.vkr.service.documentservice.dto.catalog.CatalogFullDto;
import com.gusstrik.vkr.service.documentservice.dto.request.catalog.CatalogCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;

public class CatalogServiceTest extends BaseTest {
    @Autowired
    private CatalogService catalogService;

    @BeforeEach
    public void initUser(){
        DocumentServiceCoreConfig.currentUser.set("user2");
    }

    @AfterEach
    public void destroyUser(){
        DocumentServiceCoreConfig.currentUser.remove();
    }

    @Test
    public void createNewCatalog() {
        CatalogCreateRequest createRequest = new CatalogCreateRequest();
        createRequest.setName("Тестовый вложенный каталог с правами");
        createRequest.setParentCatalogId(6L);
        System.out.println(catalogService.saveCatalog(createRequest));
    }

    @Test
    public void updateCatalog(){
        CatalogCreateRequest createRequest = new CatalogCreateRequest();
        createRequest.setId(2L);
        createRequest.setName("вложенный каталог");
//        createRequest.setParentCatalogId(4L);
        System.out.println(catalogService.saveCatalog(createRequest));
    }

    @Test
    public void deleteCatalogTest(){
        System.out.println(catalogService.deleteCatalog(1L));
    }

    @Test
    public void loadCatalog(){
        System.out.println(catalogService.loadCatalog(6L));
    }
}
