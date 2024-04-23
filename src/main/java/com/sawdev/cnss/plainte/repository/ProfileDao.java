package com.sawdev.cnss.plainte.repository;

import com.sawdev.cnss.plainte.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Repository
public interface ProfileDao extends JpaRepository<Profile, Long> {

    Optional<Profile> findByCode(String code);

    @EntityGraph(attributePaths = "privileges")
    Optional<Profile> findOneWithPrivilegesByCodeIgnoreCase(String code);

    @Modifying
    @Query(value = "DELETE FROM PROFILE_PRIVILEGE WHERE PROFILE_ID = :id", nativeQuery = true)
    int deleteAssociatePrivilege(@Param("id") Long id);
}
