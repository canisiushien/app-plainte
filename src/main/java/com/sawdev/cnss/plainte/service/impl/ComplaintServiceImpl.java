package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.configuration.SecurityUtils;
import com.sawdev.cnss.plainte.entity.Plainte;
import com.sawdev.cnss.plainte.entity.Traitement;
import com.sawdev.cnss.plainte.entity.User;
import com.sawdev.cnss.plainte.enums.EStatut;
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
        c.setStatut(EStatut.INITIAL);
        c.setDatePlainte(new Date());
        c.setNumero(this.generateNumero());
        return complaintDao.save(c);
    }

    private String generateNumero() {
        // generation du code/reference de la declaration
        Calendar myCalendar = new GregorianCalendar();
        myCalendar.setTime(new Date());
        long count = complaintDao.countByAnnee(myCalendar.get(Calendar.YEAR));

        return "P-" + myCalendar.get(Calendar.YEAR) + "-" + (count + 1);
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

    public List<Plainte> getMyComplaintDetails() {
        log.info("Liste des plaintes du user courant");
        String currentUsername = SecurityUtils.getCurrentUserUsername().get();// will get the current username
        System.out.println("________________ currentUsername : " + currentUsername);
        User user = userDao.findOneByUsername(currentUsername).get(); // by this we will get all the userdetails
        return complaintDao.findByPlaignant(user);
    }
}
