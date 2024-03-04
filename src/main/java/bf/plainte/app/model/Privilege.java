/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "privilege", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Privilege extends CommonEntity {

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
