/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.repository;

import bf.plainte.app.model.Piece;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PieceRepository extends JpaRepository<Piece, Long> {

    List<Piece> findByPlainteId(Long idePlainte);
}
