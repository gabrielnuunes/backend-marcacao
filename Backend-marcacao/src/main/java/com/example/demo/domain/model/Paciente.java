package com.example.demo.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Paciente {

	@Id
	private Long id;
	
	@Column
	private String nome;
	
}
