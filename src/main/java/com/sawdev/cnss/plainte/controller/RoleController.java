package com.sawdev.cnss.plainte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sawdev.cnss.plainte.entities.Role;
import com.sawdev.cnss.plainte.service.RoleService;

@RestController
public class RoleController {
	@Autowired
	private RoleService roleservice;

	@PreAuthorize("hasRole('Admin')")
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return roleservice.createNewRole(role);
		
	}
}
