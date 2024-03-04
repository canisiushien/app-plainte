/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.controller;

import bf.plainte.app.dto.PlainteDTO;
import bf.plainte.app.exception.CustomException;
import bf.plainte.app.service.MailService;
import bf.plainte.app.service.PlainteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping(value = "/api/public/plaintes")
@RequiredArgsConstructor
public class PlainteController {

    private final PlainteService service;

    private final MailService mailService;

    /**
     *
     * @param jsonRequest
     * @param piecesFournies
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PlainteDTO> soumettrePlainte(@RequestPart(value = "data", required = true) PlainteDTO jsonRequest, @RequestPart(value = "files", required = false) List<MultipartFile> piecesFournies) throws URISyntaxException, JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        PlainteDTO dto = objectMapper.readValue(jsonRequest, PlainteDTO.class);
        PlainteDTO response = service.create(jsonRequest, piecesFournies);

        if (response.getPlaignant() != null && mailService.isEmailValid(response.getPlaignant())) {
            mailService.sendEmail(response.getPlaignant(), "VOTRE PLAINTE", service.accuseReception(response));
        }
        return ResponseEntity.created(new URI("/api/public/plaintes/" + response.getId()))
                .body(response);
    }

    /**
     *
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    public ResponseEntity<PlainteDTO> modifierPlainte(@Valid @RequestBody PlainteDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new CustomException("Veuillez renseigner l'identifiant de la plainte SVP.");
        }
        PlainteDTO response = service.update(request);
        return ResponseEntity.ok()
                .body(response);
    }

    /**
     *
     * @param idPlainte
     * @return
     */
    @GetMapping(path = "/annulation/{id}")
    public ResponseEntity<PlainteDTO> annulerPlainte(@PathVariable(name = "id", required = true) Long idPlainte) {
        return ResponseEntity.ok().body(service.nullify(idPlainte));
    }

    /**
     *
     * @param idPlainte
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<PlainteDTO> get(@PathVariable(name = "id", required = true) Long idPlainte) {
        Optional<PlainteDTO> response = service.get(idPlainte);
        return ResponseEntity.ok().body(response.get());
    }

    /**
     *
     * @param status
     * @return
     */
    @GetMapping(path = "/plaintes-status/{status}")
    public ResponseEntity<List<PlainteDTO>> findByStatus(@PathVariable(name = "status", required = true) String status) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findByStatus(status));
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PlainteDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
}
