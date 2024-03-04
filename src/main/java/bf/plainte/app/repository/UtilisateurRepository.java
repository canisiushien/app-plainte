/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.repository;

import bf.plainte.app.model.Utilisateur;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByUsername";
    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    @EntityGraph(attributePaths = "profils")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<Utilisateur> findOneWithProfilsByUsername(String username);

    @EntityGraph(attributePaths = "profils")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<Utilisateur> findOneWithProfilsByEmailIgnoreCase(String email);
//=================================

    @Query(nativeQuery = true, value = "SELECT a.* FROM public.utilisateur a where (a.username = :username or a.email = :username) and a.actif = true")
    Optional<Utilisateur> findByUsernameOrEmailActif(String username);

    Optional<Utilisateur> findByEmailAndActifTrue(String userEmail);

    Optional<Utilisateur> findByUsernameAndActifTrue(String userUsername);
}
