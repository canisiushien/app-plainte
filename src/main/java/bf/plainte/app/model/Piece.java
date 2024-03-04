/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "piece", schema = "public")
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFichier; //nom du document uploaded

    private String extensionFichier; //extension du document uploaded

    private long tailleFichier; //taille du document uploaded

    private String urlFichier; //lien y compris nomfile

    @ManyToOne
    @JoinColumn(name = "plainte_id", nullable = false)
    private Plainte plainte; //on peut joindre un fichier à la plainte
}
