package com.sawdev.cnss.plainte.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bf.gov.conasur.pdidata.entities.utilisateurs.Profile;

/**
 * Spring Data SQL repository for the Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByCode(String code);

    @EntityGraph(attributePaths = "privileges")
    Optional<Profile> findOneWithPrivilegesByCodeIgnoreCase(String code);

    @Modifying
    @Query(value = "DELETE FROM PROFILE_PRIVILEGE WHERE PROFILE_ID = :id", nativeQuery = true)
    int deleteAssociatePrivilege(@Param("id") Long id);
}
