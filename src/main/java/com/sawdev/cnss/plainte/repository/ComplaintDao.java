package com.sawdev.cnss.plainte.repository;

import com.sawdev.cnss.plainte.entity.Plainte;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.enums.EStatut;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintDao extends CrudRepository<Plainte, Long> {

    public List<Plainte> findAll(Pageable pageable);

    public List<Plainte> findByEmail(String customerEmail);

    List<Plainte> findByPlaignant(User customer);

    List<Plainte> findByStatut(EStatut statut);

    @Query("SELECT COUNT(p) FROM Plainte p WHERE YEAR(p.datePlainte) = :annee")
    long countByAnnee(int annee);
}
