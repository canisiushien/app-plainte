package com.sawdev.cnss.plainte.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sawdev.cnss.plainte.entities.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String>{

}
