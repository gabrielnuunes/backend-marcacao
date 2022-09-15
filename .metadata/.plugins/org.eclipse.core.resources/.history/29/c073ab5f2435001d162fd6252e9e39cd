package com.example.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.exception.MedicoNaoEncontradoException;
import com.example.demo.domain.model.Medico;
import com.example.demo.domain.repository.MedicoRepository;

@Service
public class CadastroMedicoService {

	@Autowired
	MedicoRepository medicoRepository;
	
	public void excluir(Long medicoId) {
		try {
			medicoRepository.deleteById(medicoId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Medico successOrFail(Long medicoId) {
		return medicoRepository.findById(medicoId)
				.orElseThrow(() -> new MedicoNaoEncontradoException(
						String.format("Não existe um cadastro de médico com código %d", medicoId)));
	}
	
}
