package com.sawdev.cnss.plainte.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "plainte")
@Getter
@Setter
@ToString
public class Plainte extends CommonEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nom;
	
	private String prenom;
	
	private String numero;
	
	private String datePlainte;
	
	private String details;
	
	private String nature; 
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Statut statut; 

}
