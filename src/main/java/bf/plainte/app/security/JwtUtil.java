/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.security;

import bf.plainte.app.model.Privilege;
import bf.plainte.app.model.Utilisateur;
import bf.plainte.app.repository.UtilisateurRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 3 : Utilitaire pour generer un token, checker la validité/expiration d'un
 * token, extraire un user d'un token...
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expired-time}")
    private String expirationTime;

    private Key key;

    @Autowired
    private UtilisateurRepository userRepository;

    /**
     * avant la mise en service, genere une cle HMAC (Hash-based Message
     * Authentication Code) à partir d’un secret donné. Le secret est converti
     * en bytes, et la clé HMAC est ensuite stockée dans la variable key. Cette
     * key sera ensuite use entre autres pour la fabrication des tokens pour
     * user.
     */
    @PostConstruct
    public void init() {
//          byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * extrait les claims (revendicaion ou info sur le user) suivant la clé
     * spécifique ci-dessus. Le token est donc analysé valideé. Si le token est
     * invalide (par exemple, s’il a été modifié après sa création), une
     * exception sera levée.
     *
     * @param token
     * @return
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * extrait uniquement la date d'expiration d'un token donné via le claiming
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    /**
     * extrait les privileges/roles d'un token
     *
     * @param token
     * @return
     */
    public List<String> extractPrivileges(String token) {
        return (List<String>) getAllClaimsFromToken(token).get("privileges");
    }

    /**
     * controle la date d'expiration par la du jour pour dire si oui ou non le
     * token est expiré.
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    /**
     * genere pour un username donné, un token en precisant les parties de
     * username et privilèges/roles rattachés
     *
     * @param username
     * @param type
     * @return
     */
    public String generateCustomToken(String username, String type) {
        System.out.println("___________________jwtUtil.generateCustomToken(). type: " + type);
        Map<String, Object> claims = new HashMap<>();
        Utilisateur utilisateur = userRepository.findByUsernameOrEmailActif(username).orElse(null);
        Set<Privilege> privileges = utilisateur.getProfils().stream()
                .flatMap(profil -> profil.getPrivileges().stream())
                .collect(Collectors.toSet());
        List<String> privilegeNames = privileges.stream()
                .map(Privilege::getCode)
                .collect(Collectors.toList());
        claims.put("username", username);
        claims.put("privileges", privilegeNames);
        claims.put("role", type);
        return this.doGenerateToken(claims, username, type);
    }

    /**
     * constuit un token jwt (json web token)
     *
     * @param claims : les autres parties d'infos sur le user
     * @param username : le username pour qui on veut generer un token
     * @param type : le type de la methode http
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String username, String type) {
        long expirationTimeLong;
        if ("ACCESS".equals(type)) {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
        } else {
            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
        }
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    /**
     * extrait le username d'un token
     *
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * verifie la validité d'un token. est-ce que le username contenu dans le
     * token fourni correspond au username du user spring ? et est-ce que le
     * token n'est pas expiré ?
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

}
