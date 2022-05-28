package com.gusstrik.vkr.service.documentservice.repository;

import com.gusstrik.vkr.service.documentservice.model.AuthorityModel;
import com.gusstrik.vkr.service.documentservice.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityModel, Long> {

    @Query(value = "DELETE FROM authority WHERE authority.user_group_id=:id", nativeQuery = true)
    void deleteByGroup(@Param("id") Long id);

    @Query(value = "SELECT *\n" +
            "FROM authority\n" +
            "         inner join user_group ug on authority.user_group_id = ug.id\n" +
            "         inner join user_group_users ugu on ug.id = ugu.user_group_id\n" +
            "WHERE authority.writing = true\n" +
            "  and ugu.users = :user" +
            "  and authority.stored_entity_id=:id", nativeQuery = true)
    Optional<AuthorityModel> findWritingAuthority(@Param("user") String user, @Param("id") Long id);

    List<AuthorityModel> findByStoredEntityId(Long id);

    @Query(value = "SELECT authority.*\n" +
            "FROM authority\n" +
            "         left join stored_entity s on authority.stored_entity_id = s.id\n" +
            "         left join user_group ug on authority.user_group_id = ug.id\n" +
            "         left join user_group_users ugu on ug.id = ugu.user_group_id\n" +
            "WHERE ugu.users=:user and s.id = :id", nativeQuery = true)
    List<AuthorityModel> findUserAuthorities(@Param("id") Long storedEntityId, @Param("user") String user);

    void deleteAuthorityModelByStoredEntityId(Long storedEntityId);
}
