package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.configuration.SecurityUtils;
import com.sawdev.cnss.plainte.entity.Plainte;
import com.sawdev.cnss.plainte.entity.Traitement;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.enums.EStatut;
import com.sawdev.cnss.plainte.exception.CustomException;
import com.sawdev.cnss.plainte.repository.ComplaintDao;
import com.sawdev.cnss.plainte.repository.TraitementDao;
import com.sawdev.cnss.plainte.repository.UserDao;
import com.sawdev.cnss.plainte.service.ComplaintService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    public ComplaintDao complaintDao;

    @Autowired
    public TraitementDao traitementDao;

    @Autowired
    public UserDao userDao;

    private static final String COMPLAINT_RAISED = "Raised";

    //mark status as resolved
    public void markAsResolved(Long complaintId) {
        log.info("Marquer plainte resolue : {}", complaintId);
        Plainte complaint = complaintDao.findById(complaintId).get();

        if (complaint != null) {
            complaint.setStatut(EStatut.RESOLU);
            complaintDao.save(complaint);

            //rechercher les autres lignes de traitement pour les desactiver
            Traitement traitement = new Traitement();
            traitement.setActif(true);
            traitement.setDatejour(new Date());
            traitement.setPlainte(complaint);
            traitement.setStatut(EStatut.RESOLU);
            traitement.setProcessStatut("Resolved level engineer");
            traitementDao.save(traitement);
        }
//        else {
//            complaint.setComplaintStatus("Error");
//            complaintDao.save(complaint);
//        }
    }

    //mark status as in progress
    public void markAsWip(Long complaintId) {
        log.info("Marquer plainte en cours : {}", complaintId);
        Plainte complaint = complaintDao.findById(complaintId).get();

        if (complaint != null) {
            complaint.setStatut(EStatut.EN_COURS);
            complaintDao.save(complaint);

            //rechercher les autres lignes de traitement pour les desactiver
            Traitement traitement = new Traitement();
            traitement.setActif(true);
            traitement.setDatejour(new Date());
            traitement.setPlainte(complaint);
            traitement.setStatut(EStatut.EN_COURS);
            traitement.setProcessStatut("Work in progress level engineer");
            traitementDao.save(traitement);

        }
//        else {
//            complaint.setComplaintStatus("Error");
//            complaintDao.save(complaint);
//        }
    }

    //mark status as in review
    public void markAsInReview(Long complaintId) {
        log.info("Marquer plainte in review : {}", complaintId);
        Plainte complaint = complaintDao.findById(complaintId).get();
//
//        if (complaint != null) {
//            complaint.setComplaintStatus("In Review");
//            complaintDao.save(complaint);
//        } else {
//            complaint.setComplaintStatus("Error");
//            complaintDao.save(complaint);
//        }
    }

    //adding new complaint feature for customer
    public Plainte addNewComplaint(Plainte c) {
        log.info("ajout d'une nouvelle plainte : {}", c);
        User plaignant = userDao.findById(c.getPlaignant().getId()).orElseThrow(() -> new CustomException("Plaignant non identifé."));
        c.setStatut(EStatut.INITIAL);
        c.setDatePlainte(new Date());
        c.setNumero(this.generateNumero());
        c.setPlaignant(plaignant);
        return complaintDao.save(c);
    }

    @Override
    public String accuseReception(Plainte p) {
        return "Cher " + (p.getPlaignant() != null ? p.getPlaignant().getPrenom() + " " + p.getPlaignant().getNom() : "Collaborateur") + ",<br />"
                + "Nous accusons réception de votre plainte en date du " + (p.getDatePlainte() != null ? SecurityUtils.convertDateToShort(p.getDatePlainte()) : "INCONNUE") + ".<br />"
                + "Elle a été enregistrée sous le numéro de référence <b>" + p.getNumero() + "</b>.<br />"
                + "Nous nous efforçons de résoudre toutes les plaintes dans les plus brefs délais.<br />"
                + "Vous recevrez une réponse détaillée concernant votre plainte d'ici 3 jours ouvrables. Si nous avons besoin de plus de temps pour résoudre votre plainte, nous vous en informerons.<br /><br />"
                + "Cordialement,<br /><br />"
                + "L'équipe de gestion.";
    }

    private String generateNumero() {
        // generation du code/reference de la declaration
        Calendar myCalendar = new GregorianCalendar();
        myCalendar.setTime(new Date());
        long count = complaintDao.countByAnnee(myCalendar.get(Calendar.YEAR));

        return "P-" + myCalendar.get(Calendar.YEAR) + "-" + (count + 1);
    }

    @Override
    public Plainte updateComplaint(Plainte request) {
        log.info("mise a jour d'une plainte. data : {}", request);
        Plainte plainte = complaintDao.findById(request.getId()).orElseThrow(() -> new CustomException("Vous tentez de modifier une plainte qui n'existe pas."));
        if (plainte.getStatut() != EStatut.INITIAL) {
            throw new CustomException("Opération impossible ! la plainte est déjà traitée ou abandonnée.");
        }
        plainte.setDetails(request.getDetails());
        return complaintDao.save(plainte);
    }

    @Override
    public Plainte nullify(Long idPlainte) {
        log.info("annulation d'une plainte. data : {}", idPlainte);
        Plainte plainte = complaintDao.findById(idPlainte).orElseThrow(() -> new CustomException("Vous tentez d'annuler une plainte qui n'existe pas."));
        if (plainte.getStatut() != EStatut.INITIAL) {
            throw new CustomException("Opération impossible ! la plainte est déjà traitée ou abandonnée.");
        }
        plainte.setStatut(EStatut.ABANDONNE);
        return complaintDao.save(plainte);
    }

    //getting all complaints list 
    public List<Plainte> getAllComplaint() {
        log.info("Liste des plaintes");
        return (List<Plainte>) complaintDao.findAll();
    }

    public List<Plainte> getAllComplaintsForEngineer() {
        log.info("Liste des plaintes pour engg");
//        Plainte c = new Plainte();
//        String pin = c.getPincode();
//        if (pin.equals("110025")) {
//            return (List<Plainte>) complaintDao.findAll();
//        } else {
//            return null;
//        }
        return null;
    }

    //deleting complaint
    public void deleteComplaint(Long complaintId) {
        log.info("Suppression d'une plainte : {}", complaintId);
        complaintDao.deleteById(complaintId);
    }

    //getting compaints by Id
    public Plainte getComplaintById(Long complaintId) {
        log.info("Consultation d'une plainte : {}", complaintId);
        return complaintDao.findById(complaintId).get();
    }

    @Override
    public List<Plainte> findByStatut(String statut) {
        log.info("liste des plaintes par statut : {}", statut);
        return complaintDao.findByStatut(EStatut.valueOf(statut));
    }

    public List<Plainte> getMyComplaintDetails() {
        log.info("Liste des plaintes du user courant");
        String currentUsername = SecurityUtils.getCurrentUserUsername().get();// will get the current username
        User user = userDao.findOneByUsername(currentUsername).get(); // by this we will get all the userdetails
        return complaintDao.findByPlaignant(user);
    }

}
