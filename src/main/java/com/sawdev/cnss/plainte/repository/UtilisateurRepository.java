package com.sawdev.cnss.plainte.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sawdev.cnss.plainte.entities.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>, JpaSpecificationExecutor<Utilisateur> {

    String USERS_BY_LOGIN_CACHE = "usersByMatricule";
    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    @Query(value = "SELECT * FROM public.utilisateur WHERE activation_key=:token ;", nativeQuery = true)
    Optional<Utilisateur> findOneByActivationKey(@Param("token") String activationKey);

    List<Utilisateur> findAllByActifIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<Utilisateur> findOneByResetKey(String resetKey);

    Optional<Utilisateur> findOneByNomUtilisateur(String nomUtilisateur);

    Optional<Utilisateur> findOneByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "privileges")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<Utilisateur> findOneWithPrivilegesByNomUtilisateur(String nomUtilisateur);

    @EntityGraph(attributePaths = "privileges")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<Utilisateur> findOneWithPrivilegesByEmailIgnoreCase(String email);

    Page<Utilisateur> findAllByIdNotNullAndActifIsTrue(Pageable pageable);

    Optional<Utilisateur> findOneByNomUtilisateurOrEmail(String nomUtilisateur, String email);

    @Modifying
    @Query(value = "DELETE FROM UTILISATEUR_PRIVILEGE WHERE UTILISATEUR_ID = :id", nativeQuery = true)
    int deleteAssociatePrivilege(@Param("id") Long id);
}
