package com.sawdev.cnss.plainte.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sawdev.cnss.plainte.entities.Utilisateur;
import com.sawdev.cnss.plainte.repository.UtilisateurRepository;

/**
 * @author Aboubacary
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> credential = repository.findOneByNomUtilisateur(username);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }
}
