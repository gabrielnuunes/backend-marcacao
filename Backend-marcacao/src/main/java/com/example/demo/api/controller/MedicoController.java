package com.example.demo.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;
import com.example.demo.domain.exception.NegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	
	
	@PutMapping("/{medicoId}")
	public Medico atualizar(@PathVariable Long medicoId, @RequestBody Medico medico) {		
		Medico medicoAtual = cadastroMedico.successOrFail(medicoId);
		
		BeanUtils.copyProperties(medicoId, medicoAtual, "id");
		
		return medicoRepository.save(medicoAtual);
		
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable medicoId) {
		cadastroMedico.excluir(medicoId);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaExcetion(EntidadeNaoEncontradaException e) {

		Problem problema = Problem.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(NegocioException e) {

		Problem problema = Problem.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
}
