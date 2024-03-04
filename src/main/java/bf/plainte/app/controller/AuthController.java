package bf.plainte.app.controller;

import bf.plainte.app.dto.SigninDTO;
import bf.plainte.app.dto.SignupDTO;
import bf.plainte.app.service.UtilisateurService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurService authService;

    /**
     * api d'authentification
     *
     * @param param : parametre d'auth...
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninDTO param) throws JsonProcessingException {
        return authService.signin(param);
    }

    /**
     *
     * @param param
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupDTO param) throws JsonProcessingException {
        return authService.signup(param);
    }
}
