package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.entity.Plainte;
import com.sawdev.cnss.plainte.service.ComplaintService;
import com.sawdev.cnss.plainte.service.MailService;
import jakarta.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private MailService mailService;

    //only customer can register complaint
    // @PreAuthorize("hasRole('Customer')")
    @PostMapping(value = {"/customer/add/new/complaint"})
    public Plainte addNewComplaint(@RequestBody Plainte complaint) {
        Plainte response = complaintService.addNewComplaint(complaint);
        if (response.getPlaignant() != null && mailService.isEmailValid(response.getPlaignant().getEmail())) {
            mailService.sendEmail(response.getPlaignant().getEmail(), "VOTRE PLAINTE", complaintService.accuseReception(response));
        }

        return response;
    }

    /**
     * modiie des infos d'une palinte non encore traitée
     *
     * @param request
     * @return
     * @throws URISyntaxException
     */
    @PutMapping(path = "/customer/update/complaint")
    public ResponseEntity<Plainte> updateComplaint(@Valid @RequestBody Plainte request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new RuntimeException("Veuillez renseigner l'identifiant de la plainte SVP.");
        }
        Plainte response = complaintService.updateComplaint(request);
        return ResponseEntity.ok()
                .body(response);
    }

    /**
     * annuler une plainte
     *
     * @param idPlainte
     * @return
     */
    @GetMapping(path = "/customer/annulation/{id}")
    public ResponseEntity<Plainte> annulerPlainte(@PathVariable(name = "id", required = true) Long idPlainte) {
        return ResponseEntity.ok().body(complaintService.nullify(idPlainte));
    }

    //only admin can delete any complaint if as required
    //@PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = {"/admin/delete/complaint/{complaintId}"})
    public void deleteComplaint(@PathVariable("complaintId") Long complaintId) {
        complaintService.deleteComplaint(complaintId);
    }

    //only admin and manager can get all complaints list
    //@PreAuthorize("hasAnyRole('Admin','Manager','Customer')")
    @GetMapping(value = {"/customer/get/complaint/list"})
    public List<Plainte> getAllComplaints() {
        List<Plainte> complaints = complaintService.getAllComplaint();
        return complaints;
    }

    //@PreAuthorize("hasAnyRole('Engineer')")
    @GetMapping(value = {"/engineer/get/complaint/list/engineer"})
    public List<Plainte> getAllComplaintsEngg() {
        return complaintService.getAllComplaintsForEngineer();
    }

    //@PreAuthorize("hasAnyRole('Engineer')")
    @GetMapping({"/engineer/markAsResolved/{complaintId}"})
    public void markAsResolved(@PathVariable(name = "complaintId") Long complaintId) {
        complaintService.markAsResolved(complaintId);
    }

    //@PreAuthorize("hasAnyRole('Engineer')")
    @GetMapping({"/engineer/markAsWip/{complaintId}"})
    public void markAsWip(@PathVariable(name = "complaintId") Long complaintId) {
        complaintService.markAsWip(complaintId);
    }

    //@PreAuthorize("hasAnyRole('Engineer')")
    @GetMapping({"/engineer/markAsInReview/{complaintId}"})
    public void markAsInReview(@PathVariable(name = "complaintId") Long complaintId) {
        complaintService.markAsInReview(complaintId);
    }

    @GetMapping("/admin/getComplaintById/{complaintId}")
    public Plainte getComplaintById(@PathVariable Long complaintId) {
        return complaintService.getComplaintById(complaintId);
    }

    @GetMapping(path = "/customer/plaintes-statut/{statut}")
    public ResponseEntity<List<Plainte>> findByStatut(@PathVariable(name = "statut", required = true) String statut) {
        return ResponseEntity.status(HttpStatus.OK).body(complaintService.findByStatut(statut));
    }

    //@PreAuthorize("hasRole('Customer')")
    @GetMapping({"/customer/get/mycomplaints"})
    public List<Plainte> getMyComplaintDetails() {
        return complaintService.getMyComplaintDetails();
    }

}
