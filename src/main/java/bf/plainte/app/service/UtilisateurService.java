/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service;

import bf.plainte.app.dto.SigninDTO;
import bf.plainte.app.dto.SignupDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface UtilisateurService {

    ResponseEntity<?> signin(SigninDTO param) throws JsonProcessingException;//demande d'authentification

    ResponseEntity<?> signup(SignupDTO param) throws JsonProcessingException;//demande d'enregistrement new user

    ResponseEntity<?> getAllUsers() throws JsonProcessingException;//demande de la liste complete des users
}
