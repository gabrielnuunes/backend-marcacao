package com.example.demo.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.annotations.ApiModelProperty;


@Entity
@JsonRootName("paciente")
public class Paciente {

	@NotNull
	@ApiModelProperty(value = "ID do paciente", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ApiModelProperty(example = "Brasília", required = true)
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
