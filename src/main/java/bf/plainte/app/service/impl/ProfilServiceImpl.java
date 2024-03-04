/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.dto.ProfilDTO;
import bf.plainte.app.mapper.ProfilMapper;
import bf.plainte.app.model.Profil;
import bf.plainte.app.repository.ProfilRepository;
import bf.plainte.app.service.ProfilService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class ProfilServiceImpl implements ProfilService {

    @Autowired
    private ProfilRepository repository;

    @Autowired
    private ProfilMapper mapper;

    public ProfilDTO save(ProfilDTO profilDTO) {
        log.info("creation d'un profil. data = {}", profilDTO);
        Profil profile = repository.save(mapper.toEntity(profilDTO));
        return mapper.toDto(profile);
    }

    public List<ProfilDTO> findAll() {
        log.info("liste de tous les profils");
        return repository.findAll().stream()
                .map(p -> mapper.toDto(p))
                .collect(Collectors.toList());
    }

    public ProfilDTO findById(Long id) {
        log.info("consulation d'un profil. data = {}", id);
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    public ProfilDTO findByCode(String code) {
        log.info("consultation d'un profil. data = {}", code);
        return mapper.toDto(repository.findByCode(code).orElse(null));
    }

    public void delete(ProfilDTO profilDTO) {
        log.info("suppression d'un profil. data = {}", profilDTO);
        repository.delete(mapper.toEntity(profilDTO));
    }
}
