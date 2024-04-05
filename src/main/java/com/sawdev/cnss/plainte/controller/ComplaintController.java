package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.entity.Plainte;
import com.sawdev.cnss.plainte.service.ComplaintService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    //only customer can register complaint
    // @PreAuthorize("hasRole('Customer')")
    @PostMapping(value = {"/customer/add/new/complaint"})
    public Plainte addNewComplaint(@RequestBody Plainte complaint) {
        return complaintService.addNewComplaint(complaint);
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

    //@PreAuthorize("hasRole('Customer')")
    @GetMapping({"/customer/get/mycomplaints"})
    public List<Plainte> getMyComplaintDetails() {
        return complaintService.getMyComplaintDetails();
    }

}
