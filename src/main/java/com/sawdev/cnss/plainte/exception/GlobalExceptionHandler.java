/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestion globale des exceptions
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {CustomException.class/*,Exception.class,  AccessDeniedException.class, SignatureException.class, IllegalArgumentException.class*/})
    public ResponseEntity<?> handleAuthentificationException(HttpServletRequest request, CustomException ex) {
        log.error("Erreur d'authentification : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.error("Vous n'avez pas accès à la ressource : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.value(), "Vous n'avez pas accès à la ressource : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        log.error("Demande mal formulée : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Demande mal formulée : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(HttpServletRequest request, SignatureException ex) {
        log.error("Token invalide : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "Token invalide : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingClaimException.class)
    public ResponseEntity<?> handleMissingClaimException(HttpServletRequest request, MissingClaimException ex) {
        log.error("Token invalide : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "Token invalide : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(HttpServletRequest request, ExpiredJwtException ex) {
        log.error("Token expiré : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "Token expiré : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> handleInternalServerException(HttpServletRequest request, InternalServerException ex) {
        log.error("Erreur du serveur interne : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur du serveur interne : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception ex) {
        log.error("Erreur inattendue : {}", ex.getMessage());
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erreur inattendue : " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
