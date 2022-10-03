package com.example.demo.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Pacientes")
@RestController
@RequestMapping(value = "/pacientes")
public class PacienteController {
	
	@Autowired
	PacienteRepository pacienteRepository;
	
	@Autowired
	CadastroPacienteService cadastroPaciente;

	@ApiOperation("Lista todos os pacientes")
	@GetMapping
	public List<Paciente> listar() {
		return pacienteRepository.findAll();
	} 
	
	@ApiOperation("Busca um paciente pelo ID")
	@GetMapping("/{pacienteId}")
	public Paciente buscar(@PathVariable Long pacienteId) {
		return cadastroPaciente.successOrFail(pacienteId);
	}
	
	@ApiOperation("Cadastra um paciente")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Paciente adicionar(@RequestBody Paciente paciente) {
		return pacienteRepository.save(paciente);
	}
	
	@ApiOperation("Atualiza um paciente por ID")
	@PutMapping("/{pacienteId}")
	public Paciente atualizar(@PathVariable Long pacienteId, @RequestBody Paciente paciente) {
		Paciente pacienteAtual = cadastroPaciente.successOrFail(pacienteId);
		
		BeanUtils.copyProperties(paciente, pacienteAtual, "id");
		
		return pacienteRepository.save(pacienteAtual);
	}
	
	@ApiOperation("Deleta um paciente pelo ID")
	@DeleteMapping("/{pacienteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long pacienteId) {
		cadastroPaciente.excluir(pacienteId);
	}

	@ApiOperation("Atualiza parcialmente um paciente pelo ID")
	@PatchMapping("/{pacienteId}")
	public Paciente atualizarParcial(@PathVariable Long pacienteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {
		Paciente pacienteAtual = cadastroPaciente.successOrFail(pacienteId);
		
		merge(campos, pacienteAtual, null);

		return atualizar(pacienteId, pacienteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Paciente pacienteDestino,
			HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();		
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Paciente pacienteOrigem = objectMapper.convertValue(dadosOrigem, Paciente.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Paciente.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, pacienteOrigem);
				
				ReflectionUtils.setField(field, pacienteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
		

		} 

	}

