/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.exception;

/**
 * comme il n’y a pas une seule classe d’exception pour les erreurs 500,
 * celle-ci est prevue pour gerer les nombreuses exceptions différentes dans
 * l'application
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }
}
