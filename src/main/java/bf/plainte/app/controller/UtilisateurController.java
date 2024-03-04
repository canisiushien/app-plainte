package bf.plainte.app.controller;

import bf.plainte.app.service.UtilisateurService;
import bf.plainte.app.utils.AppUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping(value = "/api/utilisateurs", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService userService;

    //@PreAuthorize("hasAnyAuthority(\"" + AppUtil.ROLE_USER + "\", \"" + AppUtil.ROLE_ADMIN + "\")")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ROLE_USER + "\")")
    @GetMapping
    public ResponseEntity<?> getAllUser() throws JsonProcessingException {
        return userService.getAllUsers();
    }

}
