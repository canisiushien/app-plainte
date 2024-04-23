/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.configuration.JWTUtils;
import com.sawdev.cnss.plainte.dto.AuthRequest;
import com.sawdev.cnss.plainte.dto.AuthResponse;
import com.sawdev.cnss.plainte.entity.Privilege;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.exception.CustomException;
import com.sawdev.cnss.plainte.repository.UserDao;
import com.sawdev.cnss.plainte.service.AuthService;
import java.util.HashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JWTUtils jWTUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * creation de compte utilisateur
     *
     * @param user
     * @return
     */
    public User signUp(User user) {
        log.info("Creation de compte utilisateur : {}", user);
        throw new UnsupportedOperationException("Not implemented yet.");
//        try {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user = userDao.save(user);
//            return user;
//        } catch (Exception e) {
//            throw new RuntimeException("Erreur survenue lors de la création de compte utilisateur.");
//        }
    }

    /**
     * service d'authentification
     *
     * @param authRequest
     * @return
     */
    public AuthResponse signIn(AuthRequest authRequest) {
        log.info("Authentification de l'utilisateur : {}", authRequest.getUsername());
        AuthResponse response = new AuthResponse();

        User user = userDao.findOneByUsername(authRequest.getUsername()).orElseThrow(() -> new CustomException("Les informations d'authentification sont erronées."));
        String currentEncryptedPassword = user.getPassword();
        if (!passwordEncoder.matches(authRequest.getPassword(), currentEncryptedPassword)) {
            throw new CustomException("Les informations d'authentification sont erronées.");
        }
        if (!user.isEnabled()) {
            throw new CustomException("Le compte " + authRequest.getUsername() + " n'est pas actif.");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String jwt = jWTUtils.generateToken(user);
            String refreshToken = jWTUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime(jWTUtils.extractExpirationDate(jwt).toString());
            //A voir si vraiment necessaire de charger les roles
            response.setRoles(user.getProfile().getPrivileges().stream()
                    .map(Privilege::getCode)
                    .collect(Collectors.toSet()));
            response.setMessage(user.getUsername() + " authentifié avec succès.");
        } else {
            response.setStatusCode(500);
            response.setMessage("Echec d'authentification.");
        }
        return response;
    }

    /**
     *
     * @param token
     * @return
     */
    public AuthResponse refreshToken(String token) {
        log.info("Regeneration de token : {}", token);
        AuthResponse response = new AuthResponse();
        String username = jWTUtils.extractUsername(token);
        User user = userDao.findOneByUsername(username).orElseThrow();
        if (jWTUtils.isTokenValid(token, user)) {
            String jwt = jWTUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(token);
            response.setExpirationTime(jWTUtils.extractExpirationDate(jwt).toString());
            response.setMessage("Token rafraichi avec succès.");
        }
        response.setStatusCode(500);
        return response;
    }
}
