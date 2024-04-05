/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sawdev.cnss.plainte.service;

import com.sawdev.cnss.plainte.dto.AuthRequest;
import com.sawdev.cnss.plainte.dto.AuthResponse;
import com.sawdev.cnss.plainte.entity.User;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface AuthService {

    User signUp(User user);//creation de compte utilisateur

    AuthResponse signIn(AuthRequest authRequest);//authentification d'un user

    AuthResponse refreshToken(String token);//rafraichissement d'un token
}
