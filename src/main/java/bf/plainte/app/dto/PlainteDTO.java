/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.dto;

import bf.plainte.app.model.Piece;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
public class PlainteDTO {

    private Long id;

    private String numero;

    @NotNull(message = "Veuillez renseigner le champ objet")
    private String objet; //objet/titre de la plainte

    @NotNull(message = "Veuillez renseigner le champ contenu")
    private String contenu;//corps de la plainte

    private Date dateSoumission;//date a laquelle la plainte a ete soumise

    private String status;//plainte tratiée ou en cours de traitement

    private String plaignant; //identifié par un ID ou son email

    private List<Piece> pieces;

}
