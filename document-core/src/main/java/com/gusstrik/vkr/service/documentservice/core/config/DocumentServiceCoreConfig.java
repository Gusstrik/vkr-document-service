package com.gusstrik.vkr.service.documentservice.core.config;

import com.gusstrik.vkr.service.documentservice.repository.config.DocumentServiceRepositroyConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DocumentServiceRepositroyConfig.class)
@ComponentScan(basePackages = {"com.gusstrik.vkr.service.documentservice.core.impl"
        , "com.gusstrik.vkr.service.documentservice.core.mapper"})
public class DocumentServiceCoreConfig {
    public static ThreadLocal<String> currentUser = new ThreadLocal<>();
}
