/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sawdev.cnss.plainte.repository;

import com.sawdev.cnss.plainte.entity.Privilege;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PrivilegeDao extends JpaRepository<Privilege, String> {

    @Query("SELECT p FROM Privilege p")
    Stream<Privilege> streamAll();
}
