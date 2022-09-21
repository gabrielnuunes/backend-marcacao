package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
		
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Medico adicionar(@RequestBody Medico medico) {
		return medicoRepository.save(medico);
	}
}
