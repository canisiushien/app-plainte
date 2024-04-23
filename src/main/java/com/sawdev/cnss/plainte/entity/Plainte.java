/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.entity;

import com.sawdev.cnss.plainte.enums.EStatut;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * L'entité referencant une plainte de client/usager
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "plainte")
public class Plainte extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String numero;

    private Date datePlainte;

    @NotNull
    @Size(min = 2, max = 1000)
    @Column(name = "details", length = 1000, nullable = false)
    private String details; //detail ou contenu de la plainte

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email; //email du plaignant

    @Enumerated(EnumType.ORDINAL)
    private EStatut statut;

    @ManyToOne
    @JoinColumn(name = "plaignant_id", nullable = false)
    private User plaignant;
}
