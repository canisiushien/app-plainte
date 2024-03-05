/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service;

import bf.plainte.app.dto.PlainteDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PlainteService {

    PlainteDTO create(PlainteDTO plainteDTO, List<MultipartFile> piecesFournies);

    PlainteDTO update(PlainteDTO plainteDTO);

    PlainteDTO nullify(Long idPlainte);

    Optional<PlainteDTO> get(Long idPlainte);

    List<PlainteDTO> findByStatus(String status);

    List<PlainteDTO> findAll();

    String accuseReception(PlainteDTO plainteDTO);

    FileSystemResource downloadPieces(Long idPlainte);
}
