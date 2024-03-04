/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.repository;

import bf.plainte.app.model.Profil;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProfilRepository extends JpaRepository<Profil, Long> {

    Optional<Profil> findByCode(String code);

    @EntityGraph(attributePaths = "privileges")
    Optional<Profil> findOneWithPrivilegesByCodeIgnoreCase(String code);

    @Modifying
    @Query(value = "DELETE FROM profil_privilege WHERE profil_id = :id", nativeQuery = true)
    int deleteAssociatePrivilege(@Param("id") Long id);
}
