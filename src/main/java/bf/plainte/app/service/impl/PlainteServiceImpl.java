/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.service.impl;

import bf.plainte.app.dto.PlainteDTO;
import bf.plainte.app.enums.EStatusPlainte;
import bf.plainte.app.exception.CustomException;
import bf.plainte.app.mapper.PlainteMapper;
import bf.plainte.app.model.Plainte;
import bf.plainte.app.repository.PlainteRepository;
import bf.plainte.app.service.PlainteService;
import bf.plainte.app.utils.AppUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlainteServiceImpl implements PlainteService {

    private final PlainteRepository repository;

    private final PlainteMapper mapper;

    @Override
    public PlainteDTO create(PlainteDTO plainteDTO, List<MultipartFile> piecesFournies) {
        log.info("creation d'une plainte. data : {}", plainteDTO);
        Plainte plainte = mapper.toEntity(plainteDTO);
        plainte.setStatus(EStatusPlainte.EN_COURS);
        plainte.setDateSoumission(new Date());
        plainte.setNumero(this.numGenerator());
        plainte = repository.save(plainte);

        return mapper.toDto(plainte);
    }

    @Override
    public PlainteDTO update(PlainteDTO plainteDTO) {
        log.info("mise a jour d'une plainte. data : {}", plainteDTO);
        Plainte plainte = repository.findById(plainteDTO.getId()).orElseThrow(() -> new CustomException("Vous tentez de modifier une plainte qui n'existe pas."));
        if (plainte.getStatus() == EStatusPlainte.TRAITEE || plainte.getStatus() == EStatusPlainte.ANNULEE) {
            throw new CustomException("Opération impossible ! la plainte est déjà traitée ou annulée.");
        }
        plainte.setObjet(plainteDTO.getObjet());
        plainte.setContenu(plainteDTO.getContenu());
        plainte.setPlaignant(plainteDTO.getPlaignant());
        return mapper.toDto(repository.save(plainte));
    }

    @Override
    public PlainteDTO nullify(Long idPlainte) {
        log.info("annulation d'une plainte. data : {}", idPlainte);
        Plainte plainte = repository.findById(idPlainte).orElseThrow(() -> new CustomException("Vous tentez d'annuler une plainte qui n'existe pas."));
        plainte.setStatus(EStatusPlainte.ANNULEE);
        plainte = repository.save(plainte);
        return mapper.toDto(plainte);
    }

    @Override
    public Optional<PlainteDTO> get(Long idPlainte) {
        log.info("consultation d'une plainte. data : {}", idPlainte);
        return Optional.of(repository.findById(idPlainte))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(mapper::toDto);
    }

    @Override
    public List<PlainteDTO> findByStatus(String status) {
        log.info("liste des plaintes : {}", status);
        return repository.findByStatus(EStatusPlainte.valueOf(status)).stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PlainteDTO> findAll() {
        log.info("liste toutes les plaintes");
        return repository.findAll().stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public String accuseReception(PlainteDTO p) {
        return "Cher " + (p.getPlaignant() != null ? p.getPlaignant() : "Collaborateur") + ",<br />"
                + "Nous accusons réception de votre plainte en date du " + (p.getDateSoumission() != null ? AppUtil.convertDateToShort(p.getDateSoumission()) : "INCONNUE") + ", portant :<br />"
                + "<b>" + (p.getObjet() != null ? p.getObjet() : "SANS OBJET") + ".</b><br />"
                + "Votre plainte a été enregistrée sous le numéro de référence <b>" + p.getNumero() + "</b>.<br />"
                + "Nous nous efforçons de résoudre toutes les plaintes dans les plus brefs délais.<br />"
                + "Vous recevrez une réponse détaillée concernant votre plainte d'ici 3 jours ouvrables. Si nous avons besoin de plus de temps pour résoudre votre plainte, nous vous en informerons.<br /><br />"
                + "Cordialement,<br /><br />"
                + "L'équipe de gestion.";
    }

    private String numGenerator() {
        Calendar myCalendar = new GregorianCalendar();
        myCalendar.setTime(new Date());
        long count = repository.countByAnnee(myCalendar.get(Calendar.YEAR));
        return "PT-" + myCalendar.get(Calendar.YEAR) + "-" + (count + 1);
    }
}