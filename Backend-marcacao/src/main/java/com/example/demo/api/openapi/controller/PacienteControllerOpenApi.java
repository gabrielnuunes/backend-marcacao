package com.example.demo.api.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.api.exceptionhandler.Problem;
import com.example.demo.domain.model.Paciente;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pacientes")
public interface PacienteControllerOpenApi {

	@ApiOperation("Lista todos os pacientes")
	List<Paciente> listar();

	@ApiOperation("Busca um paciente pelo ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID de paciente inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Paciente não encontrado", response = Problem.class)

	})
	Paciente buscar(
			@ApiParam(value = "ID de um paciente", example = "1")
				Long pacienteId);

	@ApiOperation("Cadastra um paciente")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Representação de um novo paciente")
	})
	Paciente adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo paciente")
				Paciente paciente);

	@ApiOperation("Atualiza um paciente por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Paciente atualizado"),
		@ApiResponse(code = 404, message = "Paciente não encontrado", response = Problem.class)
})
	Paciente atualizar(
			@ApiParam(value = "ID de um paciente", example = "1")
				Long pacienteId, 
			@ApiParam(name = "corpo", value = "Representação de um paciente com os novos dados")	
				Paciente paciente);

	@ApiOperation("Deleta um paciente pelo ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Paciente excluído"),
		@ApiResponse(code = 404, message = "Paciente não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um paciente")
				Long pacienteId);

	@ApiOperation("Atualiza parcialmente um paciente pelo ID")
	Paciente atualizarParcial(Long pacienteId, Map<String, Object> campos, HttpServletRequest request);
	
}
