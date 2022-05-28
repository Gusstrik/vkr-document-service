package com.gusstrik.vkr.service.documentservice.repository;

import com.gusstrik.vkr.service.documentservice.model.StoredEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoredEntityRepository extends JpaRepository<StoredEntity, Long> {

    @Query(value = "WITH RECURSIVE deleteEntity as (\n" +
            "    SELECT sE1.id\n" +
            "    FROM stored_entity sE1\n" +
            "    WHERE sE1.id = :id\n" +
            "    UNION\n" +
            "    SELECT sE2.id\n" +
            "    FROM deleteEntity,\n" +
            "         stored_entity sE2\n" +
            "    WHERE deleteEntity.id = sE2.parent_catalog_id\n" +
            ")\n" +
            "DELETE\n" +
            "FROM stored_entity\n" +
            "USING deleteEntity\n" +
            "where stored_entity.id in (deleteEntity.id)", nativeQuery = true)
    void deleteInheritance(@Param("id") Long id);

    @Query(value = "SELECT stored_entity.*, 0 AS clazz_\n" +
            "FROM stored_entity\n" +
            "         left join authority a on stored_entity.id = a.stored_entity_id\n" +
            "         left join user_group ug on a.user_group_id = ug.id\n" +
            "         left join user_group_users u on ug.id = u.user_group_id\n" +
            "WHERE parent_catalog_id = :id\n" +
            "  AND (a is null OR u.users = :user)\n" +
            "ORDER BY stored_entity.type, stored_entity.name;", nativeQuery = true)
    List<StoredEntity> loadCatalog(@Param("id") Long catalogId, @Param("user") String user);
}
