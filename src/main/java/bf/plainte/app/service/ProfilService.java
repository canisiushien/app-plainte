/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service;

import bf.plainte.app.dto.ProfilDTO;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ProfilService {

    ProfilDTO save(ProfilDTO profilDTO);

    List<ProfilDTO> findAll();

    ProfilDTO findById(Long id);

    ProfilDTO findByCode(String code);

    void delete(ProfilDTO profilDTO);
}
