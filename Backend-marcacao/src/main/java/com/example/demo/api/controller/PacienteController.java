package com.example.demo.api.controller;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.model.Paciente;
import com.example.demo.domain.repository.PacienteRepository;
import com.example.demo.domain.service.CadastroPacienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
	
	@DeleteMapping("/{pacienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long pacienteId) {
		cadastroPaciente.excluir(pacienteId);
	}

}
