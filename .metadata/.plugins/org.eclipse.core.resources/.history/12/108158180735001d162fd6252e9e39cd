package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Paciente;
import com.example.demo.domain.repository.PacienteRepository;
import com.example.demo.domain.service.CadastroPacienteService;

@RestController
@RequestMapping(value = "/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	@Autowired
	CadastroPacienteService cadastroPaciente;

	@GetMapping
	public List<Paciente> listar() {
		return pacienteRepository.findAll();
	} 
	
	@GetMapping("/{pacienteId}")
	public Paciente buscar(@PathVariable Long pacienteId) {
		return cadastroPaciente.successOrFail(pacienteId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente adicionar(@RequestBody Paciente paciente) {
		return pacienteRepository.save(paciente);
	}
	
	@PutMapping("/{pacienteId}")
	public Paciente atualizar(@PathVariable Long pacienteId, @RequestBody Paciente paciente) {
		Paciente pacienteAtual = cadastroPaciente.successOrFail(pacienteId);
		
		BeanUtils.copyProperties(paciente, pacienteAtual, "id");
		
		return pacienteRepository.save(pacienteAtual);
	}
	
	
	
}
