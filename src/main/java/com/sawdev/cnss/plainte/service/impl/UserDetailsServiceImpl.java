/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.exception.CustomException;
import com.sawdev.cnss.plainte.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Le service impl relatif à la correspondance d'utilisateur. On se base sur le
 * service par defaut du spring security
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Recherche de compte utilisateur : {}", username);
        User user = userDao.findOneByUsername(username).get();
        if (!user.isEnabled()) {
            throw new CustomException("L'utilisateur " + username + " est désactivé.");
        }
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Utilisateur " + username + " introuvable.");
        }
    }

}
