/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.exception.UserNotActivatedException;
import bf.plainte.app.model.Privilege;
import bf.plainte.app.model.Utilisateur;
import bf.plainte.app.repository.UtilisateurRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("_____CustomUserDetailsServiceImpl.loadUserByUsername() username: " + username);
//        Optional<Utilisateur> user = userRepository.findByUsernameOrEmailActif(username);
//        if (user.isEmpty()) {
//            return null;
//        }
//        List<String> roles = new ArrayList<>();
//        //deb
//        Set<Privilege> privileges = new HashSet<>();
//        user.get().getProfils().stream().forEach(r -> privileges.addAll(r.getPrivileges()));
//        List<GrantedAuthority> grantedProfiles = privileges.stream()
//                .map(privilege -> new SimpleGrantedAuthority(privilege.getCode()))
//                .collect(Collectors.toList());
//        return new User(user.get().getUsername(), user.get().getPassword(), grantedProfiles);
//        //fin
//        //roles.add("USER");
//        //return User.builder().username(user.get().getUsername()).password(user.get().getPassword()).roles(roles.toArray(new String[0])).build();
//    }
    public String userId(String username) {
        Optional<Utilisateur> user = userRepository.findByUsernameAndActifTrue(username);
        String response = user.map(value -> value.getId().toString()).orElse(null);
        System.out.println("_____CustomUserDetailsServiceImpl.userId() username: " + username + " ____response: " + response);
        return response;
    }

    //======================================
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        log.info("Authenticating {}", username);

        if (new EmailValidator().isValid(username, null)) {
            return userRepository
                    .findOneWithProfilsByEmailIgnoreCase(username)
                    .map(user -> createSpringSecurityUser(username, user))
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur " + username + " inexistant dans la base de données."));
        }
        String lowercaseUsername = username.toLowerCase();
        return userRepository.findOneWithProfilsByUsername(lowercaseUsername)
                .map(user -> this.createSpringSecurityUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur " + username + " inexistant dans la base de données."));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseUsername, Utilisateur user) {
        if (!user.isActif()) {
            throw new UserNotActivatedException("Utilisateur " + lowercaseUsername + " non activé.");
        }
        Set<Privilege> privileges = new HashSet<>();
        user.getProfils().stream().forEach(r -> privileges.addAll(r.getPrivileges()));
        List<GrantedAuthority> grantedProfiles = privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getCode()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                grantedProfiles);
    }
}
