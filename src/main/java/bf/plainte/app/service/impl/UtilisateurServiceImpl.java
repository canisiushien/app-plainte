/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.dto.SigninDTO;
import bf.plainte.app.dto.SignupDTO;
import bf.plainte.app.model.Utilisateur;
import bf.plainte.app.repository.UtilisateurRepository;
import bf.plainte.app.security.JwtUtil;
import bf.plainte.app.service.UtilisateurService;
import bf.plainte.app.utils.GenerateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository userRepository;

    private final CustomUserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder encoder;

    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> signin(SigninDTO param) throws JsonProcessingException {
        log.info("Authentification username = {}", param.getUsername());
        if (param.getUsername() == null || param.getUsername().isEmpty()) {
            return GenerateResponse.badRequest(null, "Veuillez renseigner un nom d'utilisateur SVP.", null);
        }
        if (param.getPassword() == null || param.getPassword().isEmpty()) {
            return GenerateResponse.badRequest(null, "Veuillez renseigner un mot de passe SVP.", null);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(param.getUsername());
        if (userDetails == null) {
            return GenerateResponse.notFound(null, "Les informations d'authentification sont erronées", null);
        }
        if (!encoder.matches(param.getPassword(), userDetails.getPassword())) {
            return GenerateResponse.badRequest(null, "Les informations d'authentification sont erronées", null);
        }
        String jwt = jwtUtil.generateCustomToken(param.getUsername(), "ACCESS");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        return GenerateResponse.success(headers, param.getUsername() + " authentifier avec succès.", jwt);
    }

    @Override
    public ResponseEntity<?> signup(SignupDTO param) throws JsonProcessingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseEntity<?> getAllUsers() throws JsonProcessingException {
        log.info("Liste de tous les utilisateur");
        List<Utilisateur> users = userRepository.findAll();
        return GenerateResponse.success(null, "Liste d'utilisateurs trouvée.", users);
    }

}
