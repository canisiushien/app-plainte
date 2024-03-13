/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.repository;

import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bf.gov.conasur.pdidata.entities.utilisateurs.Privilege;

/**
 *
 * @author user
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, String>{
    @Query("SELECT p FROM Privilege p")
    Stream<Privilege> streamAll();
}
