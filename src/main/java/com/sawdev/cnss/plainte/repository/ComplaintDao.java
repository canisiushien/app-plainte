package com.sawdev.cnss.plainte.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sawdev.cnss.plainte.entities.Plainte;
import com.sawdev.cnss.plainte.entities.User;

@Repository
public interface ComplaintDao extends CrudRepository<Plainte, Integer>{

	public List<Plainte> findAll(Pageable pageable);
	
	public List<Plainte> findByCustomer(User user);
	
}
