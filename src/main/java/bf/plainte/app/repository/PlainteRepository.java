/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.repository;

import bf.plainte.app.enums.EStatusPlainte;
import bf.plainte.app.model.Plainte;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PlainteRepository extends JpaRepository<Plainte, Long> {

    List<Plainte> findByPlaignant(String plaignant);

    List<Plainte> findByStatus(EStatusPlainte status);

    List<Plainte> findByPlaignantAndStatus(String plaignant, EStatusPlainte status);

    @Query("SELECT COUNT(d) FROM Plainte d WHERE YEAR(d.dateSoumission) = :annee AND (d.deleted = true OR d.deleted = false)")
    long countByAnnee(int annee);
}
