package com.sawdev.cnss.plainte.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sawdev.cnss.plainte.configuration.JwtRequestFilter;
import com.sawdev.cnss.plainte.entities.Plainte;
import com.sawdev.cnss.plainte.entities.User;
import com.sawdev.cnss.plainte.repository.ComplaintDao;
import com.sawdev.cnss.plainte.repository.UserDao;

@Service
public class ComplaintService {

	@Autowired
	public ComplaintDao complaintDao;
	
	@Autowired
	public UserDao userDao;
	
	private static final String COMPLAINT_RAISED = "Raised";
	
	//mark status as resolved
	public void markAsResolved(Integer complaintId) {
		Plainte complaint = complaintDao.findById(complaintId).get();

		if (complaint != null) {
			complaint.setComplaintStatus("Resolved");
			complaintDao.save(complaint);
		}else {
			complaint.setComplaintStatus("Error");
			complaintDao.save(complaint);
		}
	}
	
	
	//mark status as in progress
	public void markAsWip(Integer complaintId) {
		Plainte complaint = complaintDao.findById(complaintId).get();

		if (complaint != null) {
			complaint.setComplaintStatus("Work in progress");
			complaintDao.save(complaint);
		}else {
			complaint.setComplaintStatus("Error");
			complaintDao.save(complaint);
		}
	}
	
	
	//mark status as in review
	public void markAsInReview(Integer complaintId) {
		Plainte complaint = complaintDao.findById(complaintId).get();

		if (complaint != null) {
			complaint.setComplaintStatus("In Review");
			complaintDao.save(complaint);
		}else {
			complaint.setComplaintStatus("Error");
			complaintDao.save(complaint);
		}
	}
	
	
	
	//adding new complaint feature for customer
	public Plainte addNewComplaint(Plainte c) {
		c.setComplaintStatus(COMPLAINT_RAISED);
		return complaintDao.save(c);
	}
	
	//getting all complaints list 
	public List<Plainte> getAllComplaint(){
		return (List<Plainte>)complaintDao.findAll();
	}
	
	public List<Plainte> getAllComplaintsForEngineer(){
		Plainte c = new Plainte();
		String pin = c.getPincode();
		if(pin.equals("110025")) {
			return (List<Plainte>)complaintDao.findAll();
		}
		else {
			return null;
		}
		
	}
	
	//deleting complaint
	public void deleteComplaint(Integer complaintId) {
		complaintDao.deleteById(complaintId);
		
	}
	
	//getting compaints by Id
	public Plainte getComplaintById(Integer complaintId) {
		return complaintDao.findById(complaintId).get();
	}
	
	public List<Plainte> getMyComplaintDetails() {
		String currentUser = JwtRequestFilter.CURRENT_USER;// will get the current username
		User user = userDao.findById(currentUser).get(); // by this we will get all the userdetails

		return complaintDao.findByCustomer(user);
		// this method described inside orderDetailDao will return list of orders for
		// that particular user
	}
}
