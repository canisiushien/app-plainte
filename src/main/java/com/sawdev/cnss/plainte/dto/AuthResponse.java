/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.dto;

import java.util.Set;
import lombok.Data;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Data
public class AuthResponse {

    private int statusCode;

    private String message;

    private String token;

    private String refreshToken;

    private String expirationTime;

    private Set<String> roles;

    public AuthResponse() {
    }

    public AuthResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
