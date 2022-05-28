package com.gusstrik.vkr.service.documentservice.repository.util;

import com.gusstrik.vkr.service.documentservice.dto.request.DocumentTypeFilter;
import com.gusstrik.vkr.service.documentservice.dto.request.UserGroupFilter;
import com.gusstrik.vkr.service.documentservice.model.DocumentType;
import com.gusstrik.vkr.service.documentservice.model.UserGroup;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserGroupSpecification {
    public static Specification<UserGroup> specification(final UserGroupFilter filter) {
        return (root, q, cB) -> {
            List<Predicate> predicates = new ArrayList();
            if (!ObjectUtils.isEmpty(filter.getQuery())) {
                predicates.add(cB.like(cB.lower(root.get("name")), "%" + filter.getQuery().trim().toLowerCase() + "%"));
            }
            if (!CollectionUtils.isEmpty(filter.getIds())) {
                predicates.add(root.get("id").in(filter.getIds()));
            }
            if (!ObjectUtils.isEmpty(filter.getUser())) {
                Join<UserGroup, String> users = root.join("users");
                predicates.add(cB.equal(users,filter.getUser()));
            }
            return cB.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }
}
