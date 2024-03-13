package com.sawdev.cnss.plainte.entities;




import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;

@Entity
public class ComplaintCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryId;
	
	private String BroadBand;
	
	private String PrePaid;
	
	private String PostPaid;
	
	private String PlansDetails;
	
	@OneToOne
	private Plainte complaint;
	
	public String getBroadBand() {
		return BroadBand;
	}
	public void setBroadBand(String broadBand) {
		BroadBand = broadBand;
	}
	public String getPrePaid() {
		return PrePaid;
	}
	public void setPrePaid(String prePaid) {
		PrePaid = prePaid;
	}
	public String getPostPaid() {
		return PostPaid;
	}
	public void setPostPaid(String postPaid) {
		PostPaid = postPaid;
	}
	public String getPlansDetails() {
		return PlansDetails;
	}
	public void setPlansDetails(String plansDetails) {
		PlansDetails = plansDetails;
	}
	public Plainte getComplaint() {
		return complaint;
	}
	public void setComplaint(Plainte complaint) {
		this.complaint = complaint;
	}
	public ComplaintCategory(String broadBand, String prePaid, String postPaid, String plansDetails,
			Plainte complaint) {
		super();
		BroadBand = broadBand;
		PrePaid = prePaid;
		PostPaid = postPaid;
		PlansDetails = plansDetails;
		this.complaint = complaint;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
	

}
