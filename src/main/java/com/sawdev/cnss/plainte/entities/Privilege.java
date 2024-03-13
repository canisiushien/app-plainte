package com.sawdev.cnss.plainte.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Table(name = "privilege")
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Privilege extends CommonEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "libelle", length = 50)
    private String libelle;

}
