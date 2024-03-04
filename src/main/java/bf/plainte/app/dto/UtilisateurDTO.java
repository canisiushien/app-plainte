/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bf.plainte.app.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class UtilisateurDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String nom;

    private String prenom;

    private boolean actif = false;

    private String activationKey;

    private String resetKey;

    private String telephone;

    private Date resetDate;

    private LocalDateTime confirmationExpireDate;

    private LocalDateTime resetExpireDate;

}
