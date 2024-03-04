/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.model;

import bf.plainte.app.enums.EStatusPlainte;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "plainte", schema = "public")
public class Plainte extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String numero;

    @Column(name = "objet", length = 150, nullable = false)
    private String objet; //objet/titre de la plainte

    @Column(name = "contenu", length = 2000, nullable = false)
    private String contenu;//corps de la plainte

    @Column(name = "date_soumission", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Africa/Ouagadougou")
    private Date dateSoumission;//date a laquelle la plainte a ete soumise

    @Enumerated(EnumType.ORDINAL)
    private EStatusPlainte status;//plainte tratiée ou en cours de traitement

    private String plaignant; //identifié par un ID ou son email

}
