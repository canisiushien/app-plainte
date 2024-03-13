package com.sawdev.cnss.plainte.configuration;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sawdev.cnss.plainte.entities.Utilisateur;

/**
 * @author Aboubacary
 */
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = -152011550925866638L;
	private String username;
    private String password;
    private boolean actif;

    public CustomUserDetails(Utilisateur userCredential) {
        this.username = userCredential.getNomUtilisateur();
        this.password = userCredential.getPassword();
        this.actif=userCredential.isActif();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return actif;
    }

    @Override
    public boolean isEnabled() {
        return actif;
    }
}
