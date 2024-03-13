package com.sawdev.cnss.plainte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sawdev.cnss.plainte.entities.Role;
import com.sawdev.cnss.plainte.repository.RoleDao;

@Service
public class RoleService {
	@Autowired //(required=true)
	private RoleDao roledao;
	
	public Role createNewRole(Role role) {
		return roledao.save(role);//will save this role and return the information saved.
		
	}

}
