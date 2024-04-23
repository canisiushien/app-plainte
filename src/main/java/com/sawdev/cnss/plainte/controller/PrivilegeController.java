/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.entity.Privilege;
import com.sawdev.cnss.plainte.service.PrivilegeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping("/privileges")
public class PrivilegeController {

    @Autowired
    private PrivilegeService service;

    @GetMapping
    public List<Privilege> findAll() {
        return service.findAll();
    }
}
