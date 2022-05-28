package com.gusstrik.vkr.service.documentservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@Data
public class AuthorityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne
    private UserGroup userGroup;

    @Column(name="reading")
    private Boolean reading;

    @Column(name ="writing")
    private Boolean writing;

    @OneToOne
    private StoredEntity storedEntity;
}
