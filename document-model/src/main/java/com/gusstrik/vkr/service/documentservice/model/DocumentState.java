package com.gusstrik.vkr.service.documentservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "document_state")
@Data
public class DocumentState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @JoinTable( name = "document_state_id",
            foreignKey = @ForeignKey(name = "document_state_FK", foreignKeyDefinition = "document_state(id)"))
    private List<String> states;

    @Column(name = "isDeleted")
    private boolean deleted;
}
