package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Paciente;
import com.example.demo.domain.repository.PacienteRepository;

@RestController
@RequestMapping(value = "/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteRepository pacienteRepository;

	@GetMapping
	public List<Paciente> listar() {
		return pacienteRepository.findAll();
	}
	
	@GetMapping("/{cozinhaId}")
	public Paciente buscar(@PathVariable cozinhaId) {
		
	}
	
}
