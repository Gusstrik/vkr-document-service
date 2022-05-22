package com.gusstrik.vkr.service.documentservice.core.test;

import com.gusstrik.vkr.service.documentservice.core.config.DocumentServiceCoreConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@Import(DocumentServiceCoreConfig.class)
@PropertySource("classpath:/test-app.properties")
public abstract class BaseTest {
}
