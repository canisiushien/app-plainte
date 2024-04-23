/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sawdev.cnss.plainte.controller;

import com.sawdev.cnss.plainte.dto.ProfileDTO;
import com.sawdev.cnss.plainte.service.ProfileService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService service;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ProfileDTO> save(@Valid @RequestBody ProfileDTO profileDTO) {
        return new ResponseEntity<>(service.save(profileDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProfileDTO> findAll() {
        return service.findAll();
    }

    @DeleteMapping
    public void delete(@RequestBody ProfileDTO profileDTO) {
        service.delete(profileDTO);
    }
}
