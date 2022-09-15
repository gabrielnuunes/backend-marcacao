package com.example.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.Paciente;
import com.example.demo.domain.repository.PacienteRepository;
import com.example.demo.domain.exception.EntidadeEmUsoException;
import com.example.demo.domain.exception.EntidadeNaoEncontradaException;

@Service
public class CadastroPacienteService {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	private static final String MSG_PACIENTE_EM_USO
		= "Paciente de código %d não pode ser removido, pois está em uso";
	
	private static final String MSG_PACIENTE_NAO_ENCONTRADO
		= "Não existe um cadastro de cozinha com código %d";

	public void excluir(Long pacienteId) {
		try {
			pacienteRepository.deleteById(pacienteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException( String.format(MSG_PACIENTE_NAO_ENCONTRADO, pacienteId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException( 
					String.format(MSG_PACIENTE_EM_USO, pacienteId));
		}
	}
	
	public Paciente successOrFail(Long pacienteId) {
		return pacienteRepository.findById(pacienteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_PACIENTE_NAO_ENCONTRADO, pacienteId)));
	}
	
}
