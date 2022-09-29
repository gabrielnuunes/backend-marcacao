package com.example.demo.api.controller;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.model.Paciente;
import com.example.demo.domain.repository.PacienteRepository;
import com.example.demo.domain.service.CadastroPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

	@PatchMapping("/{pacienteId}")
	public Paciente atualizarParcial(@PathVariable Long pacienteId, @RequestBody Map<String, Object> campos) {
		Paciente pacienteAtual = cadastroPaciente.successOrFail(pacienteId);
		merge(campos, pacienteAtual);

		return atualizar(pacienteId, pacienteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Paciente pacienteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Paciente pacienteOrigem = objectMapper.convertValue(dadosOrigem, Paciente.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Paciente.class, nomePropriedade);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, pacienteOrigem);

			ReflectionUtils.setField(field, pacienteDestino, novoValor);

		} );

	}


}
