package com.sawdev.cnss.plainte.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sawdev.cnss.plainte.entities.Plainte;
import com.sawdev.cnss.plainte.service.ComplaintService;

@RestController
@CrossOrigin
public class ComplaintController {
	@Autowired
	private ComplaintService complaintService;
	
	//only customer can register complaint
	@PreAuthorize("hasRole('Customer')")
	@PostMapping(value= {"/add/new/complaint"})
	public Plainte addNewComplaint(@RequestBody Plainte complaint) {
		return complaintService.addNewComplaint(complaint);
		
	}
	
	//only admin can delete any complaint if as required
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping(value= {"/delete/complaint/{complaintId}"})
	public void deleteComplaint(@PathVariable ("complaintId") Integer complaintId) {
		complaintService.deleteComplaint(complaintId);
	}
	
	//only admin and manager can get all complaints list
	@PreAuthorize("hasAnyRole('Admin','Manager','Customer')")
	@GetMapping(value= {"/get/complaint/list"})
	public List<Plainte> getAllComplaints() {
		List<Plainte> complaints = complaintService.getAllComplaint();
//		System.out.println("Size of complaints list id: "+ complaints.size());
		
		return complaints;
	}
	
	@PreAuthorize("hasAnyRole('Engineer')")
	@GetMapping(value= {"/get/complaint/list/engineer"})
	public List<Plainte> getAllComplaintsEngg() {
		return complaintService.getAllComplaintsForEngineer();
	}
	
	@PreAuthorize("hasAnyRole('Engineer')")
	@GetMapping({"/markAsResolved/{complaintId}"})
	public void markAsResolved(@PathVariable(name = "complaintId")Integer complaintId ) {     
		complaintService.markAsResolved(complaintId);
	}
	
	
	@PreAuthorize("hasAnyRole('Engineer')")
	@GetMapping({"/markAsWip/{complaintId}"})
	public void markAsWip(@PathVariable(name = "complaintId")Integer complaintId ) {     
		complaintService.markAsWip(complaintId);
	}
	
	@PreAuthorize("hasAnyRole('Engineer')")
	@GetMapping({"/markAsInReview/{complaintId}"})
	public void markAsInReview(@PathVariable(name = "complaintId")Integer complaintId ) {     
		complaintService.markAsInReview(complaintId);
	}
	
	@GetMapping("/getComplaintById/{complaintId}")
	public Plainte getComplaintById(@PathVariable Integer complaintId) {
		return complaintService.getComplaintById(complaintId);

	}
	
	@PreAuthorize("hasRole('Customer')")
	@GetMapping({"/get/mycomplaints"})
	public List<Plainte> getMyComplaintDetails() {
		return complaintService.getMyComplaintDetails();
	}

}
