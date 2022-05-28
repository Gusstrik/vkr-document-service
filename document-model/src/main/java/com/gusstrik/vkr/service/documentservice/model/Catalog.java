package com.gusstrik.vkr.service.documentservice.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "catalog")
@Data
public class Catalog extends StoredEntity {

}
