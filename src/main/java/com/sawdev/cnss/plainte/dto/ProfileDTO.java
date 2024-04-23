/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.dto;

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
public class ProfileDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private boolean nativeProfile = false;

    private Set<PrivilegeDTO> privileges;

    public ProfileDTO(Long id, String code, String libelle, boolean nativeProfile) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.nativeProfile = nativeProfile;
    }

}
