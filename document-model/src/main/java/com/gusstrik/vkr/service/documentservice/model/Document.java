package com.gusstrik.vkr.service.documentservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "document")
@Data
public class Document extends StoredEntity {
    @Column(name = "state")
    private String state;

    @ManyToOne
    private DocumentType documentType;

    @ElementCollection
    private Set<Long> filesId;

    @Column(name="description")
    private String description;
}
