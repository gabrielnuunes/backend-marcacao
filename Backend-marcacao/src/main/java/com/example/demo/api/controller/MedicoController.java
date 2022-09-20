package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.domain.model.Medico;
import com.example.demo.domain.repository.MedicoRepository;
import com.example.demo.domain.service.CadastroMedicoService;

public class MedicoController {

	@Autowired
	MedicoRepository medicoRepository;
	
	@Autowired
	CadastroMedicoService cadastroMedico;
	
	
	@GetMapping
	public List<Medico> listar() {
		return medicoRepository.findAll();
	}
	
	@GetMapping("/{medicoId}")
	public Medico buscar(@PathVariable Long medicoId) {
		return cadastroMedico.successOrFail(medicoId);
	}
	
}
