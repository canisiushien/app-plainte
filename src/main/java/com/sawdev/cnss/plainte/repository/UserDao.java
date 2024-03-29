package com.sawdev.cnss.plainte.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sawdev.cnss.plainte.entities.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {

}
