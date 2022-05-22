package com.gusstrik.vkr.service.documentservice.repository;

import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType,Long>, JpaSpecificationExecutor<DocumentType> {
}
