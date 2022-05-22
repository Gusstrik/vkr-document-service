package com.gusstrik.vkr.service.documentservice.repository;

import com.gusstrik.vkr.service.documentservice.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup,Long>, JpaSpecificationExecutor<UserGroup> {
}
