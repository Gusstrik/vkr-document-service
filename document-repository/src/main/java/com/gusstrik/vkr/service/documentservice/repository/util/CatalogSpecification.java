package com.gusstrik.vkr.service.documentservice.repository.util;

import com.gusstrik.vkr.service.documentservice.dto.request.DocumentStateModelFilter;
import com.gusstrik.vkr.service.documentservice.dto.request.catalog.CatalogFilter;
import com.gusstrik.vkr.service.documentservice.model.Catalog;
import com.gusstrik.vkr.service.documentservice.model.DocumentState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CatalogSpecification {
    public static Specification<Catalog> searchSpecification (final CatalogFilter filter){
        return (root,q,cB)->{
            List<Predicate> predicates = new ArrayList();
            if(!ObjectUtils.isEmpty(filter.getQuery())){
                predicates.add(cB.like(cB.lower(root.get("name")),"%"+filter.getQuery().trim().toLowerCase()+"%"));
            }
            if(filter.getParentId()!=null){
                Join<Catalog,Catalog> parentCatalog = root.join("parentCatalog");
                predicates.add(cB.equal(parentCatalog.get("id"),filter.getParentId()));
            }
            return cB.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }
}
