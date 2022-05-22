package com.gusstrik.vkr.service.documentservice.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.gusstrik.vkr.service.documentservice.model")
@EnableJpaRepositories(basePackages = "com.gusstrik.vkr.service.documentservice.repository")
public class DocumentServiceRepositroyConfig {
}
