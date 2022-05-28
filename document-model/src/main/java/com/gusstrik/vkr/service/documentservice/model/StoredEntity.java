package com.gusstrik.vkr.service.documentservice.model;

import com.gusstrik.vkr.service.documentservice.dto.enums.StoredEntityType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class StoredEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Catalog parentCatalog;

    @CreationTimestamp
    @Column(name ="create_date")
    private Date createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "author")
    private String author;

    @Column(name = "editor")
    private String editor;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StoredEntityType type;
}
