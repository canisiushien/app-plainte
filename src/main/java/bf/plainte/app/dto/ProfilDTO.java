/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bf.plainte.app.dto;

import java.io.Serializable;
import java.util.Set;
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
public class ProfilDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private boolean nativeProfil = false;

    private Set<PrivilegeDTO> privileges;
}
