/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.dto.AuthRequest;
import com.sawdev.cnss.plainte.dto.AuthResponse;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     *
     * @param user
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        return ResponseEntity.ok().body(authService.signUp(user));
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest request) {
        return ResponseEntity.ok().body(authService.signIn(request));
    }

    /**
     *
     * @param token
     * @return
     */
    @GetMapping("/refresh/{token}")
    public ResponseEntity<AuthResponse> refreshToken(@PathVariable String token) {
        return ResponseEntity.ok().body(authService.refreshToken(token));
    }
}
