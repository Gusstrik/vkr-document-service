package com.gusstrik.vkr.service.documentservice.dto;

import com.gusstrik.vkr.service.documentservice.dto.enums.StoredEntityType;
import lombok.Data;

import java.util.Date;

@Data
public class StoredEntityDto {
    private Long id;

    private String name;

    private Date createDate;

    private Date updateDate;

    private String author;

    private String editor;

    private StoredEntityType storedEntityType;

    private boolean canWrite = false;
    
}
