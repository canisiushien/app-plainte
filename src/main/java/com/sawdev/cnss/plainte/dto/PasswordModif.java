/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Setter
@Getter
@ToString
public class PasswordModif implements Serializable {

    private String newPassword;

    private String confirmNewPassword;

    private String token;
}
