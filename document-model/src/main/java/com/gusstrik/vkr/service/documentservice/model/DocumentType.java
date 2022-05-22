package com.gusstrik.vkr.service.documentservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "document_type")
@Data
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private DocumentState state;

    @Column(name = "isDeleted")
    private Boolean deleted;
}
