/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sawdev.cnss.plainte.service;

import com.sawdev.cnss.plainte.entity.Plainte;
import java.util.List;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ComplaintService {

    Plainte addNewComplaint(Plainte complaint);

    void deleteComplaint(Long complaintId);

    List<Plainte> getAllComplaint();

    List<Plainte> getAllComplaintsForEngineer();

    void markAsResolved(Long complaintId);

    void markAsWip(Long complaintId);

    void markAsInReview(Long complaintId);

    Plainte getComplaintById(Long complaintId);

    List<Plainte> getMyComplaintDetails();
}
