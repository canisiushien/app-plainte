/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.dto;

import com.sawdev.cnss.plainte.configuration.Constants;
import com.sawdev.cnss.plainte.entity.Privilege;
import com.sawdev.cnss.plainte.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
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
public class UserDTO implements Serializable {
    
    private Long id;
    
    @Size(max = 50)
    private String nom;
    
    @Size(max = 150)
    private String prenom;
    
    @Email
    @Size(min = 5, max = 254)
    private String email;
    
    private String telephone;
    
    private String password;
    
    @Pattern(regexp = Constants.LOGIN_REGEX)
    private String username;
    
    private boolean enabled = true;
    
    private ProfileDTO profile;
    
    private Set<String> privileges;
    //================================================

    public UserDTO() {
    }
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.nom = user.getNom();
        this.prenom = user.getPrenom();
        this.email = user.getEmail();
        this.telephone = user.getTelephone();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.profile = this.mapProfile(user);
        this.privileges = user.getProfile().getPrivileges().stream().map(Privilege::getCode).collect(Collectors.toSet());
    }
    
    private ProfileDTO mapProfile(User user) {
        if (user == null) {
            return null;
        }
        return new ProfileDTO(user.getProfile().getId(), user.getProfile().getCode(), user.getProfile().getLibelle(), user.getProfile().isNativeProfile());
    }
}
