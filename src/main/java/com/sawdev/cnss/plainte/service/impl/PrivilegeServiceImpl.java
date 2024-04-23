/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.service.impl;

import com.sawdev.cnss.plainte.entity.Privilege;
import com.sawdev.cnss.plainte.repository.PrivilegeDao;
import com.sawdev.cnss.plainte.service.PrivilegeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeDao repository;

    @Override
    public List<Privilege> findAll() {
        return repository.findAll();
    }

}
